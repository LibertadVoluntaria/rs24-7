/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rs247.ui;

/**
 *
 * @author Michael
 */
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.text.*;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;


/**
 * Class which handles standard buffered IO to a GUI console.
 * Supports out, err and in.
 */
public class Console extends JEditorPane {

	private JScrollPane scroll;
	private PrintStream out;
        private OutStream  outs;
	private PrintStream err;
        private OutStream errs;
	private InputStream in;

	public Console(JScrollPane parent) {
		super("text/rtf", "");
		//setTabSize(3);
		setBackground(Color.BLACK);
		setCaretColor(Color.WHITE);
		setEditable(false);
		getCaret().setVisible(false);

		this.scroll = parent;
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) ;

                outs = new OutStream(Color.GREEN);
		out = new PrintStream(outs);
                errs = new OutStream(Color.red);
		err = new PrintStream(errs);
		in = new InStream(Color.CYAN);
	}

	public PrintStream getOutStream() {
		return out;
	}

        public void setOutColor(Color c) {
            outs.setColor(c);
        }

        public void revert() {
            outs.setColor(Color.GREEN);
        }

	public PrintStream getErrStream() {
		return err;
	}

	public InputStream getInStream() {
		return in;
	}

	private static final byte[] byteArraySink = new byte[1];

	/**
	 * Moves the scroll bars so you can see what was just added
	 * Invoke on event dispatch thread only
	 */
	private void adjustScrollBars() {
		assert SwingUtilities.isEventDispatchThread();
		//TODO make it so it scrolls to the end of the ext, not just the bottom of textarea
		scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
		// scroll.getHorizontalScrollBar().setValue(scroll.getHorizontalScrollBar().getMaximum());
	}

	private class OutStream extends OutputStream {

		private SimpleAttributeSet attributeSet;

		public OutStream(Color textColor) {
			attributeSet = new SimpleAttributeSet();
			StyleConstants.setForeground(attributeSet, textColor);
		}

		@Override
		public void write(final byte[] buf, final int off, final int len) throws IOException {
			Runnable task = new Runnable() {
				public void run() {
					try {
						Console.this.getDocument().insertString(Console.this.getDocument().getLength(), new String(buf, off, len), attributeSet);
					}
					catch(BadLocationException ex) {
						ex.printStackTrace();
					}
					adjustScrollBars();
				}
			};

			if(!SwingUtilities.isEventDispatchThread()) {
				try {
					SwingUtilities.invokeAndWait(task);
				}
				catch(Exception ex) {
					if(ex instanceof InterruptedException)
						throw new InterruptedIOException(ex.getMessage());
					else
						throw new IOException(ex);
				}
			}else {
				task.run();
			}
		}

                public void setColor(Color textColor) {
                    StyleConstants.setForeground(attributeSet, textColor);
                }

		@Override
		public void write(int c) throws IOException {
			byteArraySink[0] = (byte)c;
			write(byteArraySink);
		}
	}

	private class InStream extends InputStream implements KeyListener {

		private List<Byte> keyBuffer;
		private SimpleAttributeSet attributeSet;

		public InStream(Color textColor) {
 			keyBuffer = Collections.synchronizedList(new LinkedList<Byte>());
			attributeSet = new SimpleAttributeSet();
			StyleConstants.setForeground(attributeSet, textColor);
		}

		@Override
		public int read() throws IOException {
			if(keyBuffer.isEmpty())
				fillKeyBuffer();
			return keyBuffer.remove(0).intValue();
		}

		@Override
		public int read(byte[] buf, int off, int len) throws IOException {
			if(keyBuffer.isEmpty())
				fillKeyBuffer();
			int dataLen = Math.min(len, keyBuffer.size());
			for(int i = 0; i < dataLen; i++)
				buf[off + i] = keyBuffer.remove(0).byteValue();
			return dataLen;
		}

		@Override
		public int available() throws IOException {
			return keyBuffer.size();
		}

		private void fillKeyBuffer() throws IOException {
			Console.this.addKeyListener(this);
			Console.this.setCaretPosition(Console.this.getDocument().getLength());
			getCaret().setVisible(true);
			Console.this.requestFocus();
			try {
				synchronized(attributeSet) {
					attributeSet.wait();
				}
			}
			catch(InterruptedException ex) {
				throw new InterruptedIOException(ex.getMessage());
			}
			Console.this.removeKeyListener(this);
			getCaret().setVisible(false);
		}

		public void keyTyped(KeyEvent evt) {
			//TODO find a way to stop the user clicking to move the caret
			char c = evt.getKeyChar();
			try {
				if(c == '\b' && !keyBuffer.isEmpty()) { //backspace
					Console.this.getDocument().remove(Console.this.getDocument().getLength() - 1, 1);
					keyBuffer.remove(keyBuffer.size() - 1);
					return;
				}

				Console.this.getDocument().insertString(Console.this.getDocument().getLength(), String.valueOf(c), attributeSet);
				keyBuffer.add((byte) c);
				if(c == '\r' || c == '\n') { //stop blocking
					synchronized(attributeSet) {
						attributeSet.notify();
					}
				}
			}
			catch(BadLocationException ex) {
				ex.printStackTrace();
			}
		}

		public void keyPressed(KeyEvent evt) { }
		public void keyReleased(KeyEvent evt) { }
	}

}
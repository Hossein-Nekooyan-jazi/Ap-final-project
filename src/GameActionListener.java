import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameActionListener extends KeyAdapter {
    boolean p1Move, p1Left, p1Right, p1Fire, firstpage, exit,
            p2Move, p2Left, p2Right, p2Fire,
            escape;
    Tank p1tank,p2tank;
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                this.p1Left = true;
                break;
            case KeyEvent.VK_RIGHT:
                this.p1Right = true;
                break;
            case KeyEvent.VK_UP:
                this.p1Move = true;
                break;
            case KeyEvent.VK_DOWN:
                this.p1Fire = true;
                break;
            case KeyEvent.VK_E:
                this.exit = true;
                break;
            case KeyEvent.VK_R:
                this.firstpage=true;
                break;
        }
        e.consume();
    }
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                this.p1Left = false;
                break;
            case KeyEvent.VK_RIGHT:
                this.p1Right = false;
                break;
            case KeyEvent.VK_UP:
                this.p1Move = false;
                break;
            case KeyEvent.VK_DOWN:
                this.p1Fire = false;
                break;
            case KeyEvent.VK_E:
                this.exit = false;
                break;
            case KeyEvent.VK_R:
                this.firstpage=false;
                break;
        }
        e.consume();
    }
}


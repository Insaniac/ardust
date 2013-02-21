package ardust;

import ardust.client.GameLoop;

import java.awt.*;

public class ArdustApplet extends java.applet.Applet {
    private Canvas display_parent;
    private GameLoop game;

    public ArdustApplet() {
        try {
            game = new GameLoop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setFocusTraversalKeysEnabled(false);
    }

    public void start() {

    }

    public void stop() {
        game.stopLWJGL();
    }

    public void destroy() {
        game.stopLWJGL();
        if (display_parent != null)
            remove(display_parent);
        super.destroy();
    }


    public void init() {
        setLayout(new BorderLayout());

        try {
            display_parent = new Canvas() {
                public final void addNotify() {
                    super.addNotify();
                    game.startLWJGL(display_parent);
                }

                public final void removeNotify() {
                    game.stopLWJGL();
                    super.removeNotify();
                }
            };
            //display_parent.setCursor(blankCursor);
            display_parent.setSize(getWidth(), getHeight());
            add(display_parent);
            display_parent.setFocusTraversalKeysEnabled(false);
            display_parent.setFocusable(true);
            display_parent.requestFocus();
            display_parent.setIgnoreRepaint(true);
            add(game.getMenu(), 0);
            setVisible(true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

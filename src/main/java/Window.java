import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidKeyException;

public class Window extends JFrame {
    private final JButton create, validate;
    private final JTextField strField, publicKeyField, resField;
    private final JLabel strLabel, publicKeyLabel, resLabel;

    public Window() {
        super("Lab 5");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLocation(100, 100);
        setSize(1000, 900);

        JPanel grid = new JPanel();
        GridLayout layout = new GridLayout(0, 3, 12, 12);
        grid.setLayout(layout);

        create = new JButton("Создать попись");
        validate = new JButton("Проверить подпись");

        strField = new JTextField("Мама мыла раму", 10);
        publicKeyField = new JTextField(10);
        resField = new JTextField(10);

        strLabel = new JLabel("Сообщение");
        publicKeyLabel = new JLabel("Публичный ключ");
        resLabel = new JLabel("Подпись");

        grid.add(strLabel);
        grid.add(strField);
        grid.add(create);

        grid.add(publicKeyLabel);
        grid.add(publicKeyField);
        grid.add(validate);

        grid.add(resLabel);
        grid.add(resField);

        getContentPane().add(grid);

        aHandler handler = new aHandler();
        create.addActionListener(handler);
        validate.addActionListener(handler);

        setVisible(true);
    }

    public class aHandler implements ActionListener {
        String msg = null;
        String key = null;
        String publicKey = null;
        Algorithm algorithm = new Algorithm();

        @Override
        public void actionPerformed(ActionEvent e) {
            msg = strField.getText();
            publicKey = publicKeyField.getText();
            key = resField.getText();

            try {
                if (create.equals(e.getSource())) {
                    publicKeyField.setText(algorithm.registerKeys());
                    resField.setText(algorithm.create(msg));
                }
                else if (validate.equals(e.getSource())) {
                    if (algorithm.validate(publicKey, key, msg)) {
                        JOptionPane.showMessageDialog(null, "Подпись валидна");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Подпись не валидна");
                    }
                }
                else {
                    throw new IOException("Произошла неизвестная ошибка");
                }
            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
}

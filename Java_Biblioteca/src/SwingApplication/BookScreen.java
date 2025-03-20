package SwingApplication;

import Entities.Book;
import Services.BookService;
import Utils.Database;

import jakarta.persistence.EntityManager;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import DAO.BookDAOImpl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BookScreen {
    EntityManager manager;

    private JDialog dialogFrame;

    private JTable tablePane;

    private JScrollPane scrollPane;

    private JButton button_1;
    private JButton button_2;
    private JButton button_3;
    private JButton button_4;
    private JButton button_5;

    private JLabel labelEdit;

    private JLabel label;
    private JLabel label_2;
    private JLabel label_3;
    private JLabel label_4;
    private JLabel label_5;
    private JLabel label_6;
    private JLabel label_7;
    private JLabel label_8;
    private JLabel label_9;

    private JTextField textField;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;

    public BookService bookService;
	private  BookDAOImpl bookDAO;


    public BookScreen() {
    	this.manager = Database.openConnection();
    	this.bookService = new BookService(manager);
        initialize(bookService);
    }

    private void initialize(BookService bookService) {
        dialogFrame = new JDialog();
        dialogFrame.setModal(true);
        dialogFrame.setTitle("Tela de Gerenciamento de Livros");
        dialogFrame.setBounds(100, 100, 800, 450);
        dialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialogFrame.getContentPane().setLayout(null);
        dialogFrame.addWindowListener(new java.awt.event.WindowAdapter() {

            public void windowOpened(java.awt.event.WindowEvent e) {
                Database.openConnection();
                fetchBooks();
            }

            public void windowClosing(java.awt.event.WindowEvent e) {
                Database.shutdown();
            }

        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 34, 751, 182);
        dialogFrame.getContentPane().add(scrollPane);

        tablePane = new JTable() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };


        tablePane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if(tablePane.getSelectedRow() >= 0) {
                        String bookName = (String) tablePane.getValueAt( tablePane.getSelectedRow(), 0);
                        Book bookEntity = bookService.search(bookName);
                        String bookYear = bookEntity.getYear();
                        label_2.setText(bookName);
                        label_4.setText(bookYear);
                        label.setText("");
                    }
                } catch (Exception ignored) {
                    label.setText(ignored.getMessage());
                }
            }
        });


        tablePane.setGridColor(Color.BLACK);
        tablePane.setRequestFocusEnabled(false);
        tablePane.setFocusable(false);
        tablePane.setBackground(Color.WHITE);
        tablePane.setFillsViewportHeight(true);
        tablePane.setRowSelectionAllowed(true);
        tablePane.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tablePane.setBorder(new LineBorder(new Color(0, 0, 0)));
        tablePane.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePane.setShowGrid(true);
        tablePane.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        scrollPane.setViewportView(tablePane);



        label = new JLabel("");
        label.setForeground(Color.RED);
        label.setBounds(21, 11, 751, 17);
        dialogFrame.getContentPane().add(label);

        labelEdit = new JLabel("Selecione um livro para editar: ");
        labelEdit.setBounds(21, 216, 394, 14);
        dialogFrame.getContentPane().add(labelEdit);


        /**
         * Book Title
         */
        label_2 = new JLabel("Titulo:");
        label_2.setHorizontalAlignment(SwingConstants.LEFT);
        label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
        label_2.setBounds(31, 239, 74, 14);
        dialogFrame.getContentPane().add(label_2);

        textField = new JTextField();
        textField.setFont(new Font("Dialog", Font.PLAIN, 12));
        textField.setColumns(10);
        textField.setBackground(Color.WHITE);
        textField.setBounds(101, 235, 165, 20);
        dialogFrame.getContentPane().add(textField);



        /**
         * Book Author
         */
        label_3 = new JLabel("Autor:");
        label_3.setHorizontalAlignment(SwingConstants.LEFT);
        label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
        label_3.setBounds(31, 265, 74, 14);


        dialogFrame.getContentPane().add(label_3);

        textField_2 = new JTextField();
        textField_2.setFont(new Font("Dialog", Font.PLAIN, 12));
        textField_2.setColumns(10);
        textField_2.setBackground(Color.WHITE);
        textField_2.setBounds(101, 261, 165, 20);
        dialogFrame.getContentPane().add(textField_2);




        /**
         * Book Year
         */
        label_4 = new JLabel("Ano:");
        label_4.setHorizontalAlignment(SwingConstants.LEFT);
        label_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
        label_4.setBounds(31, 290, 74, 14);
        dialogFrame.getContentPane().add(label_4);

        textField_3 = new JTextField();
        textField_3.setFont(new Font("Dialog", Font.PLAIN, 12));
        textField_3.setColumns(10);
        textField_3.setBackground(Color.WHITE);
        textField_3.setBounds(101, 286, 165, 20);
        dialogFrame.getContentPane().add(textField_3);

        /**
         * Book Genre
         */
        label_7 = new JLabel("Genero:");
        label_7.setHorizontalAlignment(SwingConstants.LEFT);
        label_7.setFont(new Font("Tahoma", Font.PLAIN, 11));
        label_7.setBounds(31, 315, 74, 14);
        dialogFrame.getContentPane().add(label_7);

        textField_6 = new JTextField();
        textField_6.setFont(new Font("Dialog", Font.PLAIN, 12));
        textField_6.setColumns(10);
        textField_6.setBackground(Color.WHITE);
        textField_6.setBounds(101, 311, 165, 20);
        dialogFrame.getContentPane().add(textField_6);


        /**
         * Book Price
         */
        label_5 = new JLabel("Preco unt.:");
        label_5.setHorizontalAlignment(SwingConstants.LEFT);
        label_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
        label_5.setBounds(31, 340, 74, 14);
        dialogFrame.getContentPane().add(label_5);

        textField_4 = new JTextField();
        textField_4.setFont(new Font("Dialog", Font.PLAIN, 12));
        textField_4.setColumns(10);
        textField_4.setBackground(Color.WHITE);
        textField_4.setBounds(101, 336, 165, 20);
        dialogFrame.getContentPane().add(textField_4);



        /**
         * Book ISBN
         */
        label_6 = new JLabel("ISBN:");
        label_6.setHorizontalAlignment(SwingConstants.LEFT);
        label_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
        label_6.setBounds(31, 365, 74, 14);
        dialogFrame.getContentPane().add(label_6);

        textField_5 = new JTextField();
        textField_5.setFont(new Font("Dialog", Font.PLAIN, 12));
        textField_5.setColumns(10);
        textField_5.setBackground(Color.WHITE);
        textField_5.setBounds(101, 361, 165, 20);
        dialogFrame.getContentPane().add(textField_5);






        // Create Button(*)
        button_1 = new JButton("Criar");
        button_1.setToolTipText("cadastrar nova pessoa");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Captura os valores dos campos de texto
                    String title = textField.getText().trim();
                    String author = textField_2.getText().trim();
                    String year = textField_3.getText().trim();
                    String genre = textField_6.getText().trim();
                    String price = textField_4.getText().trim();
                    String isbn = textField_5.getText().trim();


                    // Validação básica para não permitir campos vazios
                    if (title.isEmpty() || author.isEmpty() || price.isEmpty() || isbn.isEmpty() || genre.isEmpty() || year.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Double priceText = Double.parseDouble(price);
                    bookService.create(isbn, title, author, year, genre, priceText);

                    label.setText("Livro cadastrado com sucesso!");
                    fetchBooks();
                }
                catch(Exception ex) {
                    label.setText(ex.getMessage());
                }
            }
        });
        button_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
        button_1.setBounds(400, 352, 74, 48);
        dialogFrame.getContentPane().add(button_1);



        // Update button ()
        button_2 = new JButton("Atualizar");
        button_2.setToolTipText("Atualizar livro: ");
        button_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Captura os valores dos campos de texto
                    String title = textField.getText().trim();
                    String author = textField_2.getText().trim();
                    String year = textField_3.getText().trim();
                    String genre = textField_6.getText().trim();
                    Double price = Double.valueOf(textField_4.getText().trim());
                    String isbn = textField_5.getText().trim();

                    String newTitle = textField.getText().trim();
                    String newAuthor = textField.getText().trim();
                    Double newPrice = Double.valueOf(textField.getText().trim());
                    String newIsbn = textField.getText().trim();
                    String newGenre = textField.getText().trim();
                    String newYear = textField.getText().trim();


                    // Validação básica para não permitir campos vazios
                    if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || genre.isEmpty() || year.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }


                    bookService.update(title, newTitle, newAuthor, newPrice, newIsbn, newGenre, newYear);

                    JOptionPane.showMessageDialog(null, "Livro atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    fetchBooks();
                }

                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Preço inválido. Insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }

                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar livro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        button_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
        button_2.setBounds(565, 358, 89, 28);
        dialogFrame.getContentPane().add(button_2);



        button_3 = new JButton("Deletar");
        button_3.setToolTipText("apagar pessoa e seus dados");
        button_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if(textField.getText().isEmpty()) {
                        label_2.setText("nome vazio");
                        return;
                    }

                    String bookName = textField.getText();
                    Object[] options = { "Confirmar", "Cancelar" };
                    int escolha = JOptionPane
                            .showOptionDialog(null,
                                    "Esta opcao ira apagar o Livro " +bookName,
                                    "Alerta",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE,
                                    null,
                                    options,
                                    options[1]);

                    if(escolha == 0) {
                        bookService.delete(bookName);
                        label.setText("Livro deletado com sucesso!");
                        fetchBooks();
                    } else
                        label.setText("Operacao cancelada!");

                } catch (Exception ignored) {
                    label.setText(ignored.getMessage());
                }
            }
        });
        button_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
        button_3.setBounds(480, 359, 80, 28);
        dialogFrame.getContentPane().add(button_3);


        /**
         *
         *  Refresh List?
         *
         */
        button_4 = new JButton("Limpar");
        button_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
                textField_2.setText("");
                textField_3.setText("");
                textField_4.setText("");
                textField_5.setText("");
                textField_6.setText("");
            }
        });
        button_4.setBounds(664, 358, 89, 28);
        dialogFrame.getContentPane().add(button_4);



        label_8 = new JLabel("Novo Livro");
        label_8.setHorizontalAlignment(SwingConstants.LEFT);
        label_8.setFont(new Font("Tahoma", Font.PLAIN, 11));
        label_8.setBounds(400, 236, 74, 20);

        // TITULO

        dialogFrame.getContentPane().add(label_8);

        textField_4 = new JTextField();
        textField_4.setFont(new Font("Dialog", Font.PLAIN, 12));
        textField_4.setColumns(10);
        textField_4.setBounds(470, 235, 165, 20);
        dialogFrame.getContentPane().add(textField_4);

        dialogFrame.setVisible(true);
    }

    public void fetchBooks() {
        try {
            // Criar um novo modelo de tabela
            DefaultTableModel tableModel = new DefaultTableModel();

            // Adicionar colunas à tabela
            tableModel.addColumn("Title");
            tableModel.addColumn("Author");
            tableModel.addColumn("Genre");
            tableModel.addColumn("Year");
            tableModel.addColumn("ISBN");
            tableModel.addColumn("Price");

            // Buscar lista de livros do banco
            List<Book> bookList = bookService.list();

            // Adicionar os livros ao modelo da tabela
            for (Book book : bookList) {
                tableModel.addRow(new Object[]{
                        book.getTitle(),
                        book.getAuthor(),
                        book.getGenre(),
                        book.getYear(),
                        book.getIsbn(),
                        book.getPrice()
                });
            }

            // Atualizar a JTable com os novos dados
            tablePane.setModel(tableModel);
            label_9.setText("Resultados: " + bookList.size() + " livros encontrados.");

            // Ajustar a largura das colunas automaticamente
            tablePane.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        } catch (Exception erro) {
            label.setText(erro.getMessage());
        }

    }
}

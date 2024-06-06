package UI.automata;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class UI_automata1 extends javax.swing.JFrame {

    public static String nameSelect1 = "";
    public static String dateSelect1 = "";

    public Color SetColor1() {
        return new Color(104, 105, 106);
    }

//    String str, int numState, String [] tranFunction, String startState, String finalState
    public boolean TestString(String str, int numState, String[] tranFunction, String startState, String finalState) {
        boolean IsAccept = false;

        String strNew = str;
        int numStateNew = numState;

        String[] stateGenerate = new String[numStateNew];
        for (int i = 0; i < numStateNew; i++) {
            stateGenerate[i] = "q" + i;
        }

        // String symbolsNew[] = {"a","b"};
        // {"q0=>q1,a","q0=>q0,b","q1=>q1,a","q1=>q2,b","q2=>q2,a","q2=>q2,b"};
        String tranFunctionNew[] = tranFunction;
        String startStateNew = startState;
        String finalStartNew = finalState;

        String testSymbol = "";

        String currentState = startStateNew;
//        char currentSymbol;
//        
        String myTransitionWithSymbol = "";

        for (int i = 0; i < strNew.length(); i++) {
            testSymbol = String.valueOf(strNew.charAt(i));

            for (int j = 0; j < numStateNew; j++) {

                myTransitionWithSymbol = startStateNew + "=>" + stateGenerate[j] + "," + testSymbol;
                for (String tran : tranFunctionNew) {
                    if (myTransitionWithSymbol.equals(tran)) {
                        currentState = stateGenerate[j];
                        System.out.println(currentState);
                        break;
                    }
                }
                if (finalStartNew.contains(currentState)) {
                    IsAccept = true;
                }

            }

            startStateNew = currentState;
        }

        if (IsAccept == true) {
            System.out.println("Accept!");
        } else {
            System.out.println("Reject!");
        }

        return IsAccept;

    }

    /**
     * Creates new form UI_automata2
     */
    public UI_automata1() {
        // constructor run at the first time.
//        String dfName = "Haha";
//        String date = "2024-06-07";
//        Object[] row = {dfName, date};
//
//        DefaultTableModel model;
//      
//        
//        model = (DefaultTableModel) RecentTable.getModel();
//        model.addRow(row);

        initComponents();
        UpdateTable();
        setTitle("UI automata2");
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        Main2.setVisible(false);
    }

    // class for get user input
    class Automaton {

        private int numberOfState;
        private String setofSymbols;
        private String transitionFunctions;
        private String startState;
        private String setofFinalStates;

        private String name;

        private String date;

        @Override
        public String toString() {
            return "Automaton "
                    + "Number of States: " + numberOfState + "\n"
                    + "Set of Symbols: " + setofSymbols + "\n"
                    + "Transition Functions: " + transitionFunctions + "\n"
                    + "Start State: " + startState + "\n"
                    + "Set of Final States: " + setofFinalStates;
        }

//        public Automaton(int numberOfState,String setofSymbols,  String [] transitionFunctions, String startState, String [] setofFinalStates ) {
//            this.numberOfState = numberOfState;
//            this.setofSymbols = setofSymbols;
//            this.transitionFunctions = transitionFunctions;
//            this.startState = startState;
//            this.setofFinalStates = setofFinalStates;
//            
//        }
        /**
         * @return the numberOfState
         */
        public int getNumberOfState() {
            return numberOfState;
        }

        /**
         * @param numberOfState the numberOfState to set
         */
        public void setNumberOfState(int numberOfState) {
            if (numberOfState < 0) {
                JOptionPane.showMessageDialog(rootPane, "Number of State must be greater than 0!", "Automaton Processor", ERROR);
            } else {
                this.numberOfState = numberOfState;
            }

        }

        /**
         * @return the setofSymbols
         */
        public String getSetofSymbols() {
            return setofSymbols;
        }

        /**
         * @param setofSymbols the setofSymbols to set
         */
        public void setSetofSymbols(String setofSymbols) {
            this.setofSymbols = setofSymbols;
        }

        /**
         * @return the transitionFunctions
         */
        public String getTransitionFunctions() {
            return transitionFunctions;
        }

        /**
         * @param transitionFunctions the transitionFunctions to set
         */
        public void setTransitionFunctions(String transitionFunctions) {
            this.transitionFunctions = transitionFunctions;
        }

        /**
         * @return the startState
         */
        public String getStartState() {
            return startState;
        }

        /**
         * @param startState the startState to set
         */
        public void setStartState(String startState) {
            this.startState = startState;
        }

        /**
         * @return the setofFinalStates
         */
        public String getSetofFinalStates() {
            return setofFinalStates;
        }

        /**
         * @param setofFinalStates the setofFinalStates to set
         */
        public void setSetofFinalStates(String setofFinalStates) {
            this.setofFinalStates = setofFinalStates;
        }

        /**
         * @return the date
         */
        public String getDate() {
            return date;
        }

        /**
         * @param date the date to set
         */
        public void setDate(String date) {
            this.date = date;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

    }

    // initial table here 
    public void UpdateTable() {
//
//        String name = "Haha";
//        String date = "2024-06-07";

        ArrayList<Automaton> automatonList = new ArrayList<Automaton>();

        try {
            Connection con = DBConnection2.getConnection();
            String name = "";
            String date = "";
            int StateNum = 0;
            String symbols = "";
            String transitions = "";
            String startstate = "";
            String finalstate = "";

            PreparedStatement pst = con.prepareStatement("select * from automatadetails ");
//            pst.setString(1, name);
//            pst.setString(2, date);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
                date = rs.getString("TheDate");
                StateNum = rs.getInt("StateNum");
                symbols = rs.getString("symbols");
                transitions = rs.getString("transitions");
                startstate = rs.getString("startstate");
                finalstate = rs.getString("finalstate");

                // Create a new Automaton object for each row
                Automaton auto = new Automaton();

                auto.setName(name); // Assuming 'name' is set elsewhere
                auto.setDate(date); // Assuming 'date' is set elsewhere (these might not be needed if not stored)
                auto.setNumberOfState(StateNum);
                auto.setSetofSymbols(symbols);
                auto.setTransitionFunctions(transitions);
                auto.setStartState(startstate);
                auto.setSetofFinalStates(finalstate);

                automatonList.add(auto); // Add the Automaton object to the list
            }

            // Handle the case where no data is found (optional)
            if (automatonList.isEmpty()) {
                System.out.println("No data found in the database.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources (assuming these are managed outside this block)
            // rs.close();
            // pst.close();
            // con.close();
        }

        DefaultTableModel model = (DefaultTableModel) RecentTable.getModel();
        model.setRowCount(0); /// clear table first
        for (Automaton auto : automatonList) {
            String nameInserted = auto.getName(); // Assuming a getter method for name in Automaton
            String dateInserted = auto.getDate(); // Assuming a getter method for date in Automaton
            Object[] row = {nameInserted, dateInserted};
            model.addRow(row);
        }
    }

    public boolean insertValid(String name, String TheDate) {

        boolean IsValid = false;

        Connection myCon = DBConnection2.getConnection();
        try {
            // Check for duplicate entry
            String checkSql = "SELECT COUNT(*) FROM automatadetails WHERE name = ? AND TheDate = ?";
            PreparedStatement checkPst = myCon.prepareStatement(checkSql);
            checkPst.setString(1, name);
            checkPst.setString(2, TheDate);

            ResultSet rs = checkPst.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if (count > 0) {

                IsValid = IsValid;
            } else {
                IsValid = !IsValid;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return IsValid;

    }

    public void insertAutomaton(String name, String TheDate, int StateNum, String symbols, String transitions, String startstate, String finalstart) {
        Connection myCon = DBConnection2.getConnection();
        try {
            // Check for duplicate entry
            boolean isInsertValid = insertValid(name, TheDate);
            // Proceed with insertion if no duplicate is found
            if (isInsertValid) {
                String sqlStatement = "INSERT INTO automatadetails(name, TheDate, StateNum, symbols, transitions, startstate, finalstate) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = myCon.prepareStatement(sqlStatement);

                pst.setString(1, name);
                pst.setString(2, TheDate);
                pst.setInt(3, StateNum);
                pst.setString(4, symbols);
                pst.setString(5, transitions);
                pst.setString(6, startstate);
                pst.setString(7, finalstart);

                int updateRowCount = pst.executeUpdate(); // Check if insert is successful
                if (updateRowCount > 0) {
                    JOptionPane.showMessageDialog(null, "Record Inserted Successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Record Inserted Unsuccessfully");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Record with the same name and date already exists.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources if needed
            // rs.close();
            // checkPst.close();
            // pst.close();
            // myCon.close();
        }
        UpdateTable();
    }

    public void deleteFA(String name, String date) {
        String nameSelected = name;
        String dateSelected = date;
        Connection myCon = DBConnection2.getConnection();

        try {
            String sqlStatement = "DELETE FROM automatadetails WHERE name = ? and TheDate = ?";

            PreparedStatement pst = myCon.prepareStatement(sqlStatement);

            pst.setString(1, nameSelected);
            pst.setString(2, dateSelected);

            int updateRowCount = pst.executeUpdate(); // check if delete is successful

            if (updateRowCount > 0) {
                JOptionPane.showConfirmDialog(this, "Record Deleted Successfully");
            } else {
                JOptionPane.showConfirmDialog(this, "Record Deletion Unsuccessful");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Main1 = new javax.swing.JPanel();
        headerName = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        setOfStatesField = new javax.swing.JTextField();
        setOfSymbolsField = new javax.swing.JTextField();
        transitionFunctionField = new javax.swing.JTextField();
        startStateField = new javax.swing.JTextField();
        setOfFinalStatesFields = new javax.swing.JTextField();
        btnMinimize = new javax.swing.JButton();
        BtnTeststring = new javax.swing.JButton();
        Main2 = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        btnTesting = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnCheckFA = new javax.swing.JButton();
        btnConstruct = new javax.swing.JButton();
        testStringfield = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        BtnRefresh = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        OutputArea = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        RecentTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        Btnload = new javax.swing.JButton();
        Btnupdate = new javax.swing.JButton();
        Btndelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 255, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Main1.setBackground(new java.awt.Color(255, 255, 255));

        headerName.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        headerName.setText("Automaton Processor");
        headerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                headerNameActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel3.setText("Input set of symbols");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel4.setText("Input transition functions");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel5.setText("Input number of states");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel6.setText("Input set of final states");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel7.setText("Input start state");

        setOfStatesField.setFont(new java.awt.Font("Times New Roman", 0, 21)); // NOI18N
        setOfStatesField.setForeground(new java.awt.Color(102, 102, 102));
        setOfStatesField.setText("2");
        setOfStatesField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                setOfStatesFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                setOfStatesFieldFocusLost(evt);
            }
        });
        setOfStatesField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setOfStatesFieldActionPerformed(evt);
            }
        });

        setOfSymbolsField.setFont(new java.awt.Font("Times New Roman", 0, 21)); // NOI18N
        setOfSymbolsField.setForeground(new java.awt.Color(102, 102, 102));
        setOfSymbolsField.setText("{a, b}");
        setOfSymbolsField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                setOfSymbolsFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                setOfSymbolsFieldFocusLost(evt);
            }
        });
        setOfSymbolsField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setOfSymbolsFieldActionPerformed(evt);
            }
        });

        transitionFunctionField.setFont(new java.awt.Font("Times New Roman", 0, 21)); // NOI18N
        transitionFunctionField.setForeground(new java.awt.Color(102, 102, 102));
        transitionFunctionField.setText("{q0=>q1, a};{q0=>q0, b}");
        transitionFunctionField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                transitionFunctionFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                transitionFunctionFieldFocusLost(evt);
            }
        });
        transitionFunctionField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transitionFunctionFieldActionPerformed(evt);
            }
        });

        startStateField.setFont(new java.awt.Font("Times New Roman", 0, 21)); // NOI18N
        startStateField.setForeground(new java.awt.Color(102, 102, 102));
        startStateField.setText("q0");
        startStateField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                startStateFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                startStateFieldFocusLost(evt);
            }
        });
        startStateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startStateFieldActionPerformed(evt);
            }
        });

        setOfFinalStatesFields.setFont(new java.awt.Font("Times New Roman", 0, 21)); // NOI18N
        setOfFinalStatesFields.setForeground(new java.awt.Color(102, 102, 102));
        setOfFinalStatesFields.setText("{q1}");
        setOfFinalStatesFields.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                setOfFinalStatesFieldsFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                setOfFinalStatesFieldsFocusLost(evt);
            }
        });
        setOfFinalStatesFields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setOfFinalStatesFieldsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(startStateField, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(transitionFunctionField, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(setOfFinalStatesFields, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(82, 82, 82)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(setOfStatesField, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(setOfSymbolsField, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 32, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setOfStatesField, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(setOfSymbolsField, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(transitionFunctionField, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startStateField, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setOfFinalStatesFields, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                .addGap(0, 24, Short.MAX_VALUE))
        );

        btnMinimize.setBackground(new java.awt.Color(0, 0, 255));
        btnMinimize.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        btnMinimize.setForeground(new java.awt.Color(255, 255, 255));
        btnMinimize.setText("Minimize FA");
        btnMinimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinimizeActionPerformed(evt);
            }
        });

        BtnTeststring.setBackground(new java.awt.Color(0, 0, 255));
        BtnTeststring.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        BtnTeststring.setForeground(new java.awt.Color(255, 255, 255));
        BtnTeststring.setText("Test string");
        BtnTeststring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTeststringActionPerformed(evt);
            }
        });

        Main2.setBackground(new java.awt.Color(255, 255, 255));

        btnBack.setBackground(new java.awt.Color(0, 0, 255));
        btnBack.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnTesting.setBackground(new java.awt.Color(0, 0, 255));
        btnTesting.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        btnTesting.setForeground(new java.awt.Color(255, 255, 255));
        btnTesting.setText("Test");
        btnTesting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestingActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel9.setText("Testing String");

        javax.swing.GroupLayout Main2Layout = new javax.swing.GroupLayout(Main2);
        Main2.setLayout(Main2Layout);
        Main2Layout.setHorizontalGroup(
            Main2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main2Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(Main2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Main2Layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(119, 119, 119)
                        .addComponent(btnTesting, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(193, 193, 193))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Main2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(270, 270, 270))))
        );
        Main2Layout.setVerticalGroup(
            Main2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(244, 244, 244)
                .addGroup(Main2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTesting, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(326, Short.MAX_VALUE))
        );

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/rsz_1rsz_1rsz_1rsz_1pencil.png"))); // NOI18N

        btnCheckFA.setBackground(new java.awt.Color(0, 0, 255));
        btnCheckFA.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        btnCheckFA.setForeground(new java.awt.Color(255, 255, 255));
        btnCheckFA.setText("Check FA");
        btnCheckFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckFAActionPerformed(evt);
            }
        });

        btnConstruct.setBackground(new java.awt.Color(0, 0, 255));
        btnConstruct.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        btnConstruct.setForeground(new java.awt.Color(255, 255, 255));
        btnConstruct.setText("Construct FA");
        btnConstruct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConstructActionPerformed(evt);
            }
        });

        testStringfield.setFont(new java.awt.Font("Times New Roman", 0, 21)); // NOI18N
        testStringfield.setForeground(new java.awt.Color(153, 153, 153));
        testStringfield.setText("aabb");
        testStringfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                testStringfieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                testStringfieldFocusLost(evt);
            }
        });
        testStringfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testStringfieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Main1Layout = new javax.swing.GroupLayout(Main1);
        Main1.setLayout(Main1Layout);
        Main1Layout.setHorizontalGroup(
            Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main1Layout.createSequentialGroup()
                .addGroup(Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Main1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(Main1Layout.createSequentialGroup()
                                .addGroup(Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(Main1Layout.createSequentialGroup()
                                        .addComponent(btnCheckFA, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(50, 50, 50)
                                        .addComponent(btnConstruct, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(testStringfield))
                                .addGap(46, 46, 46)
                                .addGroup(Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BtnTeststring, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(Main1Layout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(headerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Main2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        Main1Layout.setVerticalGroup(
            Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Main2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(Main1Layout.createSequentialGroup()
                        .addGroup(Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(headerName, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addGroup(Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConstruct, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCheckFA, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnTeststring, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(testStringfield, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(Main1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 690));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setText("Output");

        BtnRefresh.setBackground(new java.awt.Color(0, 0, 255));
        BtnRefresh.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        BtnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        BtnRefresh.setText("Refresh");
        BtnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshActionPerformed(evt);
            }
        });

        OutputArea.setColumns(20);
        OutputArea.setFont(new java.awt.Font("Times New Roman", 0, 21)); // NOI18N
        OutputArea.setForeground(new java.awt.Color(0, 0, 204));
        OutputArea.setRows(5);
        jScrollPane2.setViewportView(OutputArea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(136, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(BtnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(BtnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 500, 290));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        RecentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Date"
            }
        ));
        RecentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RecentTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(RecentTable);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 47, 470, 210));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("Recent FAs");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 6, 136, -1));

        Btnload.setBackground(new java.awt.Color(0, 0, 255));
        Btnload.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        Btnload.setForeground(new java.awt.Color(255, 255, 255));
        Btnload.setText("Load");
        Btnload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnloadActionPerformed(evt);
            }
        });
        jPanel3.add(Btnload, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 140, 50));

        Btnupdate.setBackground(new java.awt.Color(0, 0, 255));
        Btnupdate.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        Btnupdate.setForeground(new java.awt.Color(255, 255, 255));
        Btnupdate.setText("Update");
        Btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnupdateActionPerformed(evt);
            }
        });
        jPanel3.add(Btnupdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 290, 140, 50));

        Btndelete.setBackground(new java.awt.Color(0, 0, 255));
        Btndelete.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        Btndelete.setForeground(new java.awt.Color(255, 255, 255));
        Btndelete.setText("Delete");
        Btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtndeleteActionPerformed(evt);
            }
        });
        jPanel3.add(Btndelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 290, 130, 50));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 300, 500, 390));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void headerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_headerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_headerNameActionPerformed

    private void BtnTeststringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTeststringActionPerformed
        // TODO add your handling code here

        int numState = Integer.parseInt(setOfStatesField.getText());
        String setOfSymbol = setOfSymbolsField.getText();
        String transitionFunctionNew[] = (transitionFunctionField.getText()).split("[{;}]", 0);
        String startState = startStateField.getText();
        String setFinalState = setOfFinalStatesFields.getText();

        String stringToTest = testStringfield.getText();
        String contentTest = "";

        boolean IsAcceptMyString = TestString(stringToTest, numState, transitionFunctionNew, startState, setFinalState);

        if (IsAcceptMyString == true) {
            contentTest = "=> String \"" + stringToTest + "\" is accepted!";
        } else {
            contentTest = "=> String \"" + stringToTest + "\" is rejected!";
        }
        String name = "Test 2";
        String StateNum = "3";
        try {
            Connection con = DBConnection2.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from automatadetails where name = ? and StateNum = ?");
            pst.setString(1, name);
            pst.setString(2, StateNum);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Connect Successfully!");

            } else {
                JOptionPane.showMessageDialog(this, "Incorrect name or stateNum!");
            }

            // this will return boolean
        } catch (Exception e) {
            e.printStackTrace();
        }

        OutputArea.setText(contentTest);


    }//GEN-LAST:event_BtnTeststringActionPerformed

    private void btnMinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinimizeActionPerformed
        // TODO add your handling code here:
        // Write code to get user input here!


    }//GEN-LAST:event_btnMinimizeActionPerformed

    private void BtnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshActionPerformed
        // TODO add your handling code here:
        OutputArea.setText("");

    }//GEN-LAST:event_BtnRefreshActionPerformed

    private void BtnloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnloadActionPerformed
        // TODO add your handling code here:

        String nameSelected = "";
        String dateSelected = "";
        DefaultTableModel model = (DefaultTableModel) RecentTable.getModel();

        int selectedRowIndex = RecentTable.getSelectedRow();

        if (selectedRowIndex >= 0) {
            // Access row data (replace with your model's logic)
            nameSelected = (String) model.getValueAt(selectedRowIndex, 0); // Assuming name is in column 0
            dateSelected = (String) model.getValueAt(selectedRowIndex, 1); // Assuming date is in column 1

            // Optional: Database interaction based on data from the selected row
            // ...
            System.out.println("Selected row: Name - " + nameSelected + ", Date - " + dateSelected);
        } else {
            System.out.println("No row selected");
        }

        String name = nameSelected;
        String date = dateSelected;

        try {
            Connection con = DBConnection2.getConnection();

            int StateNum = 0;
            String symbols = "";
            String transitions = "";
            String startstate = "";
            String finalstart = "";

            PreparedStatement pst = con.prepareStatement("select * from automatadetails where name = ? and TheDate = ?");
            pst.setString(1, name);
            pst.setString(2, date);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // JOptionPane.showMessageDialog(this, "Connect Successfully!");

                StateNum = rs.getInt("StateNum");
                symbols = rs.getString("symbols");
                transitions = rs.getString("transitions");
                startstate = rs.getString("startstate");
                finalstart = rs.getString("finalstate");

                Automaton auto = new Automaton();

                auto.setName(name);
                auto.setDate(date);
                auto.setNumberOfState(StateNum);
                auto.setSetofSymbols(symbols);
                auto.setTransitionFunctions(transitions);
                auto.setStartState(startstate);
                auto.setSetofFinalStates(finalstart);

                System.out.println(auto.toString());

            } else {
                // JOptionPane.showMessageDialog(this, "Incorrect name or stateNum!");
            }

            // Set data into text fields
            headerName.setText(name);
            setOfStatesField.setText(String.valueOf(StateNum));
            setOfSymbolsField.setText(symbols);
            transitionFunctionField.setText(transitions);
            startStateField.setText(startstate);
            setOfFinalStatesFields.setText(finalstart);

            // this will return boolean
        } catch (Exception e) {
            e.printStackTrace();
        }
        // try to load data from SQL 
        // user name and date value from user select on table row
//        
//        String name = "Test 2";
//        String date = "2024-06-05";
//        
//      
//        try {
//            Connection con = DBConnection2.getConnection();
//            
//           
//            int StateNum = 0;
//            String symbols = "";
//            String transitions = "";
//            String startstate = "";
//            String finalstart  = "";
//            
//            PreparedStatement pst = con.prepareStatement("select * from automatadetails where name = ? and TheDate = ?");
//            pst.setString(1, name);
//            pst.setString(2, date);
//            ResultSet rs = pst.executeQuery();
//            if (rs.next()) {
//                JOptionPane.showMessageDialog(this, "Connect Successfully!");
//                
//                
//                StateNum = rs.getInt("StateNum");
//                symbols = rs.getString("symbols");
//                transitions  = rs.getString("transitions");
//                startstate = rs.getString("startstate");
//                finalstart = rs.getString("finalstate");
//                
//               Automaton auto = new Automaton();
//               
//               auto.setName(name);
//               auto.setDate(date);
//               auto.setNumberOfState(StateNum);
//               auto.setSetofSymbols(symbols);
//               auto.setTransitionFunctions(transitions);
//               auto.setStartState(startstate);
//               auto.setSetofFinalStates(finalstart);
//               
//                System.out.println( auto.toString());
//               
//
//            } else {
//                JOptionPane.showMessageDialog(this, "Incorrect name or stateNum!");
//            }
//
//            // this will return boolean
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }//GEN-LAST:event_BtnloadActionPerformed

    private void BtnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnupdateActionPerformed
        // TODO add your handling code here:

        String oldName = "";
        String oldDate = "";

        DefaultTableModel model = (DefaultTableModel) RecentTable.getModel();

        int selectedRowIndex = RecentTable.getSelectedRow();

        if (selectedRowIndex >= 0) {
            // Access row data (replace with your model's logic)
            oldName = (String) model.getValueAt(selectedRowIndex, 0); // Assuming name is in column 0
            oldDate = (String) model.getValueAt(selectedRowIndex, 1); // Assuming date is in column 1

            // Optional: Database interaction based on data from the selected row
            // ...
        } else {
            System.out.println("Data not found!");
        }

        int numState = Integer.parseInt(setOfStatesField.getText());
        String setOfSymbol = setOfSymbolsField.getText();
        String transitionFunction = transitionFunctionField.getText();
        String startState = startStateField.getText();
        String setFinalState = setOfFinalStatesFields.getText();

        String dfName = headerName.getText();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Automaton auto = new Automaton();
        auto.setNumberOfState(numState);
        auto.setSetofSymbols(setOfSymbol);
        auto.setTransitionFunctions(transitionFunction);
        auto.setStartState(startState);
        auto.setSetofFinalStates(setFinalState);
        auto.setName(dfName);
        auto.setDate(date);

        try {
            Connection con = DBConnection2.getConnection();
            String sqlStatement = "UPDATE automatadetails SET name = ?, TheDate = ?, StateNum = ?, symbols = ?, transitions = ?, startstate = ?, finalstate = ? WHERE name = ? and TheDate = ?";

            PreparedStatement pst = con.prepareStatement(sqlStatement);

            pst.setString(1, auto.getName()); // New name
            pst.setString(2, auto.getDate());
            pst.setInt(3, auto.getNumberOfState());
            pst.setString(4, auto.getSetofSymbols());
            pst.setString(5, auto.getTransitionFunctions());
            pst.setString(6, auto.getStartState());
            pst.setString(7, auto.getSetofFinalStates());
            pst.setString(8, oldName); // Old name to identify the record to update
            pst.setString(9, oldDate);

            int updateRowCount = pst.executeUpdate(); // check if update is successful

            if (updateRowCount > 0) {
                JOptionPane.showMessageDialog(null, "Record Updated Successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Record Update Unsuccessful");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources if needed (assuming these are managed outside this block)
            // pst.close();
            // con.close();
        }

        UpdateTable();

    }//GEN-LAST:event_BtnupdateActionPerformed

    private void BtndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtndeleteActionPerformed
        // TODO add your handling code here:

        String nameSelected = "";
        String dateSelected = "";

        DefaultTableModel model = (DefaultTableModel) RecentTable.getModel();

        int selectedRowIndex = RecentTable.getSelectedRow();

        if (selectedRowIndex >= 0) {
            // Access row data (replace with your model's logic)
            nameSelected = (String) model.getValueAt(selectedRowIndex, 0); // Assuming name is in column 0
            dateSelected = (String) model.getValueAt(selectedRowIndex, 1); // Assuming date is in column 1

            // Optional: Database interaction based on data from the selected row
            // ...
        } else {
            System.out.println("Data not found!");
        }
        
        deleteFA(nameSelected, dateSelected);

        UpdateTable();


    }//GEN-LAST:event_BtndeleteActionPerformed

    private void setOfSymbolsFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setOfSymbolsFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_setOfSymbolsFieldActionPerformed

    private void transitionFunctionFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transitionFunctionFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_transitionFunctionFieldActionPerformed

    private void startStateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startStateFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startStateFieldActionPerformed

    private void setOfFinalStatesFieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setOfFinalStatesFieldsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_setOfFinalStatesFieldsActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:

        UI_automata1 ui1 = new UI_automata1();
        UI_automata2 ui2 = new UI_automata2();

        ui2.dispose();
        ui2.setEnabled(false);
        ui2.setVisible(false);
        ui1.setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnTestingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTestingActionPerformed

    private void setOfStatesFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setOfStatesFieldActionPerformed
        // TODO add your handling code here:

//        int numStates =  Integer.parseInt(setOfStatesField1.getText());
//        String setOfSymbols = setOfSymbolsField.getText();
//        String transitionFunction = transitionFunctionField.getText();
//        String startState = startStateField.getText();
//        String setOfFinalStat = setOfFinalStatesFields.getText();
//
//        System.out.println(numStates);
//        System.out.println(setOfSymbols);
//        System.out.println(transitionFunction);
//        System.out.println(startState);
//        System.out.println(setOfFinalStat);

    }//GEN-LAST:event_setOfStatesFieldActionPerformed

    private void setOfStatesFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_setOfStatesFieldFocusGained
        // TODO add your handling code here:
        if (setOfStatesField.getText().equals("2")) {
            setOfStatesField.setText("");
            setOfStatesField.setForeground(SetColor1());
        }
    }//GEN-LAST:event_setOfStatesFieldFocusGained

    private void setOfSymbolsFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_setOfSymbolsFieldFocusGained
        // TODO add your handling code here:

        if (setOfSymbolsField.getText().equals("{a, b}")) {
            setOfSymbolsField.setText("");
            setOfSymbolsField.setForeground(SetColor1());
        }
    }//GEN-LAST:event_setOfSymbolsFieldFocusGained

    private void transitionFunctionFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_transitionFunctionFieldFocusGained
        // TODO add your handling code here:

        if (transitionFunctionField.getText().equals("{q0=>q1, a};{q0=>q0, b}")) {
            transitionFunctionField.setText("");
            transitionFunctionField.setForeground(SetColor1());
        }
    }//GEN-LAST:event_transitionFunctionFieldFocusGained

    private void setOfStatesFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_setOfStatesFieldFocusLost
        // TODO add your handling code here:
        if (setOfStatesField.getText().equals("")) {
            setOfStatesField.setText("2");
            setOfStatesField.setForeground(SetColor1());
        }
    }//GEN-LAST:event_setOfStatesFieldFocusLost

    private void setOfSymbolsFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_setOfSymbolsFieldFocusLost
        // TODO add your handling code here:
        if (setOfSymbolsField.getText().equals("")) {
            setOfSymbolsField.setText("{a, b}");
            setOfSymbolsField.setForeground(SetColor1());
        }
    }//GEN-LAST:event_setOfSymbolsFieldFocusLost

    private void transitionFunctionFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_transitionFunctionFieldFocusLost
        // TODO add your handling code here:
        if (transitionFunctionField.getText().equals("")) {
            transitionFunctionField.setText("{q0=>q1, a};{q0=>q0, b}");
            transitionFunctionField.setForeground(SetColor1());
        }
    }//GEN-LAST:event_transitionFunctionFieldFocusLost

    private void startStateFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_startStateFieldFocusGained
        // TODO add your handling code here:
        if (startStateField.getText().equals("q0")) {
            startStateField.setText("");
            startStateField.setForeground(SetColor1());
        }


    }//GEN-LAST:event_startStateFieldFocusGained

    private void startStateFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_startStateFieldFocusLost
        // TODO add your handling code here:
        if (startStateField.getText().equals("")) {
            startStateField.setText("q0");
            startStateField.setForeground(SetColor1());
        }
    }//GEN-LAST:event_startStateFieldFocusLost

    private void setOfFinalStatesFieldsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_setOfFinalStatesFieldsFocusGained
        // TODO add your handling code here:
        if (setOfFinalStatesFields.getText().equals("{q1}")) {
            setOfFinalStatesFields.setText("");
            setOfFinalStatesFields.setForeground(SetColor1());
        }
    }//GEN-LAST:event_setOfFinalStatesFieldsFocusGained

    private void setOfFinalStatesFieldsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_setOfFinalStatesFieldsFocusLost
        // TODO add your handling code here:
        if (setOfFinalStatesFields.getText().equals("")) {
            setOfFinalStatesFields.setText("{q1}");
            setOfFinalStatesFields.setForeground(SetColor1());
        }
    }//GEN-LAST:event_setOfFinalStatesFieldsFocusLost

    private void btnCheckFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckFAActionPerformed
        // TODO add your handling code here:

        int numState = Integer.parseInt(setOfStatesField.getText());
        String setOfSymbol = setOfSymbolsField.getText();
        String transitionFunction = transitionFunctionField.getText();
        String startState = startStateField.getText();
        String setFinalState = setOfFinalStatesFields.getText();

        String dfName = headerName.getText();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        /// check FA here 
        //1. no epsilon transition
        //2. number of transitions equal to number of states x number of symbols
        // how to count number of symbol
        // how to count number of transition
        String mySymbol[] = setOfSymbol.split("[{,} ]", 0);
        String myTranf[] = transitionFunction.split(";", 0);

        for (String s : myTranf) {
            System.out.println(s);
        }

        int numOfTranf = myTranf.length;

        System.out.println("num of transition functions=" + numOfTranf);

        // str = "ab"
        System.out.println(setOfSymbol);

        String joinedStr = String.join("", mySymbol);

        int numOfSymbol = joinedStr.length();

        boolean IsEqual;
        String messageDFA = " \n => It is a NFA";

        IsEqual = (numOfSymbol * numState) == numOfTranf;

        System.out.println(IsEqual);
        if ((!(transitionFunction.contains("ε"))) && IsEqual) {
            messageDFA = " \n => It is a DFA";

        }

        Automaton auto = new Automaton();

//        Object[] row = {dfName, date};
//
//        DefaultTableModel model = (DefaultTableModel) RecentTable.getModel();
//
//        model.addRow(row);

        auto.setNumberOfState(numState);
        auto.setSetofSymbols(setOfSymbol);
        auto.setTransitionFunctions(transitionFunction);
        auto.setStartState(startState);
        auto.setSetofFinalStates(setFinalState);

        System.out.println(auto.toString());

        String myContent = auto.toString();

        String myRealMeassage = myContent + "\n" + messageDFA;
        // Create and set up the text area
        OutputArea.setText(myRealMeassage);
        OutputArea.setEditable(false);  // Make the text area read-only
        OutputArea.setLineWrap(true);   // Enable line wrapping
        OutputArea.setWrapStyleWord(true); // Wrap at word boundaries

        String name = dfName;
        String TheDate = date;
        int StateNum = numState;
        String symbols = setOfSymbol;
        String transitions = transitionFunction;
        String startstate = startState;
        String finalstart = setFinalState;

        // use function insert FA
        insertAutomaton(name, TheDate, StateNum, symbols, transitions, startstate, finalstart);

        // clear user input
        headerName.setText("");

        setOfStatesField.setText("");
        setOfSymbolsField.setText("");
        transitionFunctionField.setText("");
        startStateField.setText("");
        setOfFinalStatesFields.setText("");

        // use function split string before insert into object of class

    }//GEN-LAST:event_btnCheckFAActionPerformed

    private void btnConstructActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConstructActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnConstructActionPerformed

    private void testStringfieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_testStringfieldFocusGained
        // TODO add your handling code here:

        if (testStringfield.getText().equals("aabb")) {
            testStringfield.setText("");
            testStringfield.setForeground(SetColor1());
        }

    }//GEN-LAST:event_testStringfieldFocusGained

    private void testStringfieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_testStringfieldFocusLost
        // TODO add your handling code here:

        if (testStringfield.getText().equals("")) {
            testStringfield.setText("aabb");
            testStringfield.setForeground(SetColor1());
        }

    }//GEN-LAST:event_testStringfieldFocusLost

    private void testStringfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testStringfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_testStringfieldActionPerformed

    private void RecentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RecentTableMouseClicked
        // TODO add your handling code here:

//        String nameSelected = "";
//        String dateSelected = "";
//        DefaultTableModel model = (DefaultTableModel) RecentTable.getModel();
//
//        int selectedRowIndex = RecentTable.getSelectedRow();
//
//        if (selectedRowIndex >= 0) {
//            // Access row data (replace with your model's logic)
//            nameSelected = (String) model.getValueAt(selectedRowIndex, 0); // Assuming name is in column 0
//            dateSelected = (String) model.getValueAt(selectedRowIndex, 1); // Assuming date is in column 1
//
//            // Optional: Database interaction based on data from the selected row
//            // ...
//            System.out.println("Selected row: Name - " + nameSelected + ", Date - " + dateSelected);
//        } else {
//            System.out.println("No row selected");
//        }
//
//        String name = nameSelected;
//        String date = dateSelected;
//
//        try {
//            Connection con = DBConnection2.getConnection();
//
//            int StateNum = 0;
//            String symbols = "";
//            String transitions = "";
//            String startstate = "";
//            String finalstart = "";
//
//            PreparedStatement pst = con.prepareStatement("select * from automatadetails where name = ? and TheDate = ?");
//            pst.setString(1, name);
//            pst.setString(2, date);
//            ResultSet rs = pst.executeQuery();
//            if (rs.next()) {
//                // JOptionPane.showMessageDialog(this, "Connect Successfully!");
//
//                StateNum = rs.getInt("StateNum");
//                symbols = rs.getString("symbols");
//                transitions = rs.getString("transitions");
//                startstate = rs.getString("startstate");
//                finalstart = rs.getString("finalstate");
//
//                Automaton auto = new Automaton();
//
//                auto.setName(name);
//                auto.setDate(date);
//                auto.setNumberOfState(StateNum);
//                auto.setSetofSymbols(symbols);
//                auto.setTransitionFunctions(transitions);
//                auto.setStartState(startstate);
//                auto.setSetofFinalStates(finalstart);
//
//                System.out.println(auto.toString());
//
//            } else {
//                // JOptionPane.showMessageDialog(this, "Incorrect name or stateNum!");
//            }
//
//            // Set data into text fields
//            headerName.setText(name);
//            setOfStatesField.setText(String.valueOf(StateNum));
//            setOfSymbolsField.setText(symbols);
//            transitionFunctionField.setText(transitions);
//            startStateField.setText(startstate);
//            setOfFinalStatesFields.setText(finalstart);
//
//            // this will return boolean
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }//GEN-LAST:event_RecentTableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UI_automata1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI_automata1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI_automata1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI_automata1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UI_automata1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
                for (GraphicsDevice device : devices) {
                    new UI_automata1().setVisible(true);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnRefresh;
    private javax.swing.JButton BtnTeststring;
    private javax.swing.JButton Btndelete;
    private javax.swing.JButton Btnload;
    private javax.swing.JButton Btnupdate;
    private javax.swing.JPanel Main1;
    private javax.swing.JPanel Main2;
    private javax.swing.JTextArea OutputArea;
    private javax.swing.JTable RecentTable;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCheckFA;
    private javax.swing.JButton btnConstruct;
    private javax.swing.JButton btnMinimize;
    private javax.swing.JButton btnTesting;
    private javax.swing.JTextField headerName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField setOfFinalStatesFields;
    private javax.swing.JTextField setOfStatesField;
    private javax.swing.JTextField setOfSymbolsField;
    private javax.swing.JTextField startStateField;
    private javax.swing.JTextField testStringfield;
    private javax.swing.JTextField transitionFunctionField;
    // End of variables declaration//GEN-END:variables
}

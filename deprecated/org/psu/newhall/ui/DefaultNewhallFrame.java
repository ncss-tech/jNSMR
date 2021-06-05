/*      */ package org.psu.newhall.ui;public class DefaultNewhallFrame extends JFrame { private NewhallDataset nd; private NewhallResults nr; private boolean inMetric = true; private boolean loaded = false; private JMenuItem aboutMenuItem; private JLabel activeUnitSystemLabel;
/*      */   private JLabel activeUnitSystemText;
/*      */   private JLabel address;
/*      */   private JSpinner amplitudeSpinner;
/*      */   private JLabel annualRainfallText;
/*      */   private JPanel calendarPanel;
/*      */   private JLabel city;
/*      */   private JLabel contribCountryLabel;
/*      */   private JPanel contributorPanel;
/*      */   private JLabel country;
/*      */   private JLabel countryLabel;
/*      */   private JLabel countryText;
/*      */   private JPanel datasetPanel;
/*      */   private JScrollPane datasetScrollPane;
/*      */   private JScrollPane datasetScrollPane1;
/*      */   private JScrollPane datasetScrollPane2;
/*      */   private JScrollPane datasetScrollPane3;
/*      */   private JLabel elevation;
/*      */   private JLabel elevationLabel;
/*      */   private JLabel elevationText;
/*      */   private JLabel email;
/*      */   private JLabel emailLabel;
/*      */   private JLabel endingYearLabel;
/*      */   private JLabel endingYearText;
/*      */   private JMenuItem exitMenuItem;
/*      */   private JButton exportCsvButton;
/*      */   
/*      */   public DefaultNewhallFrame() {
/*   29 */     initComponents();
/*   30 */     setTitle("Newhall " + Newhall.NSM_VERSION);
/*   31 */     this.whcSpinner.setValue(Double.valueOf(200.0D));
/*   32 */     this.whcSpinner.setEnabled(false);
/*      */   }
/*      */   private JButton exportXmlButton; private JMenu fileMenu; private JLabel firstName; private JMenu helpMenu; private JPanel inputPanel; private JLabel jLabel1; private JLabel jLabel10; private JLabel jLabel11; private JLabel jLabel12; private JLabel jLabel13; private JLabel jLabel14; private JLabel jLabel15; private JLabel jLabel16; private JLabel jLabel17; private JLabel jLabel18; private JLabel jLabel2; private JLabel jLabel21; private JLabel jLabel23; private JLabel jLabel3; private JLabel jLabel4; private JLabel jLabel5; private JLabel jLabel6; private JLabel jLabel7; private JLabel jLabel8; private JLabel jLabel9; private JPanel jPanel1; private JPanel jPanel3; private JScrollPane jScrollPane1; private JScrollPane jScrollPane2; private JScrollPane jScrollPane3; private JScrollPane jScrollPane4; private JScrollPane jScrollPane5; private JScrollPane jScrollPane6; private JScrollPane jScrollPane7; private JScrollPane jScrollPane8; private JSeparator jSeparator1; private JSeparator jSeparator2; private JSeparator jSeparator3; private JTextArea jTextArea1; private JTextArea jTextArea2; private JTextArea jTextArea3; private JTextArea jTextArea4; private JLabel lastName; private JLabel latitudeLabel; private JLabel latitudeText; private JLabel longitudeLabel; private JLabel longitudeText; private JMenuBar menuBar; private JLabel mlraId; private JLabel mlraName; private JPanel modelResultsPanel; private JPanel moistCalPanel; private JTextArea moistureCalendarText; private JLabel moistureRegimeText; private JTable mpeTable; private JTextArea notes; private JPanel notesPanel; private JMenuItem openDatasetMenuItem; private JMenu optionsMenu; private JLabel orginization; private JLabel phone; private JLabel phoneLabel; private JLabel postal; private JLabel postalLabel; private JTable rainfallTable; private JTable rainfallTable1; private JSpinner soilAirOffset; private JLabel soilAirOffsetLabel; private JLabel soilAirOffsetUnits; private JLabel srcUnitSystem; private JLabel srcUnitSystemLabel; private JLabel startYearLabel; private JLabel startingYearText; private JLabel stateProv; private JLabel stateProvLabel; private JLabel stationCountry; private JLabel stationId; private JLabel stationLabel; private JLabel stationName; private JLabel stationNameLabel; private JLabel stationStateProv; private JLabel stationText; private JTextArea statisticsText; private JLabel subdivisionsLabel; private JLabel subdivisionsText; private JTabbedPane tabPane; private JPanel tempCalPanel;
/*      */   private JTable tempTable;
/*      */   private JTextArea temperatureCalendarText;
/*      */   private JLabel temperatureRegimeText;
/*      */   private JLabel title;
/*      */   private JMenuItem toggleUnitsMenuItem;
/*      */   private JSpinner whcSpinner;
/*      */   private JLabel whcUnitsText;
/*      */   
/*      */   private void initComponents() {
/*   44 */     this.jSeparator1 = new JSeparator();
/*   45 */     this.datasetScrollPane2 = new JScrollPane();
/*   46 */     this.rainfallTable1 = new JTable();
/*   47 */     this.tabPane = new JTabbedPane();
/*   48 */     this.inputPanel = new JPanel();
/*   49 */     this.datasetPanel = new JPanel();
/*   50 */     this.datasetScrollPane = new JScrollPane();
/*   51 */     this.rainfallTable = new JTable();
/*   52 */     this.datasetScrollPane3 = new JScrollPane();
/*   53 */     this.tempTable = new JTable();
/*   54 */     this.stationLabel = new JLabel();
/*   55 */     this.stationText = new JLabel();
/*   56 */     this.elevationLabel = new JLabel();
/*   57 */     this.elevationText = new JLabel();
/*   58 */     this.latitudeLabel = new JLabel();
/*   59 */     this.latitudeText = new JLabel();
/*   60 */     this.longitudeLabel = new JLabel();
/*   61 */     this.longitudeText = new JLabel();
/*   62 */     this.countryLabel = new JLabel();
/*   63 */     this.countryText = new JLabel();
/*   64 */     this.startYearLabel = new JLabel();
/*   65 */     this.startingYearText = new JLabel();
/*   66 */     this.endingYearLabel = new JLabel();
/*   67 */     this.endingYearText = new JLabel();
/*   68 */     this.srcUnitSystemLabel = new JLabel();
/*   69 */     this.srcUnitSystem = new JLabel();
/*   70 */     this.jSeparator2 = new JSeparator();
/*   71 */     this.soilAirOffsetLabel = new JLabel();
/*   72 */     this.soilAirOffset = new JSpinner();
/*   73 */     this.soilAirOffsetUnits = new JLabel();
/*   74 */     this.jSeparator3 = new JSeparator();
/*   75 */     this.jLabel21 = new JLabel();
/*   76 */     this.jLabel23 = new JLabel();
/*   77 */     this.amplitudeSpinner = new JSpinner();
/*   78 */     this.calendarPanel = new JPanel();
/*   79 */     this.tempCalPanel = new JPanel();
/*   80 */     this.jScrollPane4 = new JScrollPane();
/*   81 */     this.jTextArea3 = new JTextArea();
/*   82 */     this.jScrollPane5 = new JScrollPane();
/*   83 */     this.jTextArea4 = new JTextArea();
/*   84 */     this.jScrollPane6 = new JScrollPane();
/*   85 */     this.temperatureCalendarText = new JTextArea();
/*   86 */     this.jLabel8 = new JLabel();
/*   87 */     this.moistCalPanel = new JPanel();
/*   88 */     this.jScrollPane2 = new JScrollPane();
/*   89 */     this.jTextArea1 = new JTextArea();
/*   90 */     this.jScrollPane3 = new JScrollPane();
/*   91 */     this.jTextArea2 = new JTextArea();
/*   92 */     this.jScrollPane1 = new JScrollPane();
/*   93 */     this.moistureCalendarText = new JTextArea();
/*   94 */     this.jLabel7 = new JLabel();
/*   95 */     this.modelResultsPanel = new JPanel();
/*   96 */     this.jLabel3 = new JLabel();
/*   97 */     this.jLabel4 = new JLabel();
/*   98 */     this.jLabel5 = new JLabel();
/*   99 */     this.datasetScrollPane1 = new JScrollPane();
/*  100 */     this.mpeTable = new JTable();
/*  101 */     this.moistureRegimeText = new JLabel();
/*  102 */     this.temperatureRegimeText = new JLabel();
/*  103 */     this.annualRainfallText = new JLabel();
/*  104 */     this.jPanel3 = new JPanel();
/*  105 */     this.jScrollPane7 = new JScrollPane();
/*  106 */     this.statisticsText = new JTextArea();
/*  107 */     this.subdivisionsLabel = new JLabel();
/*  108 */     this.subdivisionsText = new JLabel();
/*  109 */     this.jPanel1 = new JPanel();
/*  110 */     this.stationNameLabel = new JLabel();
/*  111 */     this.jLabel1 = new JLabel();
/*  112 */     this.jLabel2 = new JLabel();
/*  113 */     this.jLabel9 = new JLabel();
/*  114 */     this.jLabel10 = new JLabel();
/*  115 */     this.jLabel11 = new JLabel();
/*  116 */     this.jLabel12 = new JLabel();
/*  117 */     this.contributorPanel = new JPanel();
/*  118 */     this.jLabel13 = new JLabel();
/*  119 */     this.jLabel14 = new JLabel();
/*  120 */     this.jLabel15 = new JLabel();
/*  121 */     this.jLabel16 = new JLabel();
/*  122 */     this.jLabel17 = new JLabel();
/*  123 */     this.jLabel18 = new JLabel();
/*  124 */     this.stateProvLabel = new JLabel();
/*  125 */     this.postalLabel = new JLabel();
/*  126 */     this.contribCountryLabel = new JLabel();
/*  127 */     this.emailLabel = new JLabel();
/*  128 */     this.phoneLabel = new JLabel();
/*  129 */     this.firstName = new JLabel();
/*  130 */     this.lastName = new JLabel();
/*  131 */     this.title = new JLabel();
/*  132 */     this.orginization = new JLabel();
/*  133 */     this.address = new JLabel();
/*  134 */     this.city = new JLabel();
/*  135 */     this.stateProv = new JLabel();
/*  136 */     this.postal = new JLabel();
/*  137 */     this.country = new JLabel();
/*  138 */     this.email = new JLabel();
/*  139 */     this.phone = new JLabel();
/*  140 */     this.notesPanel = new JPanel();
/*  141 */     this.jScrollPane8 = new JScrollPane();
/*  142 */     this.notes = new JTextArea();
/*  143 */     this.stationName = new JLabel();
/*  144 */     this.stationId = new JLabel();
/*  145 */     this.elevation = new JLabel();
/*  146 */     this.stationStateProv = new JLabel();
/*  147 */     this.stationCountry = new JLabel();
/*  148 */     this.mlraName = new JLabel();
/*  149 */     this.mlraId = new JLabel();
/*  150 */     this.jLabel6 = new JLabel();
/*  151 */     this.whcSpinner = new JSpinner();
/*  152 */     this.whcUnitsText = new JLabel();
/*  153 */     this.exportXmlButton = new JButton();
/*  154 */     this.exportCsvButton = new JButton();
/*  155 */     this.activeUnitSystemLabel = new JLabel();
/*  156 */     this.activeUnitSystemText = new JLabel();
/*  157 */     this.menuBar = new JMenuBar();
/*  158 */     this.fileMenu = new JMenu();
/*  159 */     this.openDatasetMenuItem = new JMenuItem();
/*  160 */     this.exitMenuItem = new JMenuItem();
/*  161 */     this.optionsMenu = new JMenu();
/*  162 */     this.toggleUnitsMenuItem = new JMenuItem();
/*  163 */     this.helpMenu = new JMenu();
/*  164 */     this.aboutMenuItem = new JMenuItem();
/*      */     
/*  166 */     this.rainfallTable1.setFont(this.rainfallTable1.getFont());
/*  167 */     this.rainfallTable1.setModel(new DefaultTableModel(new Object[][] { { "", null, null, null, null, null, null, null, null, null, null, null, null }, , { "", null, null, null, null, null, null, null, null, null, null, null, null },  }, (Object[])new String[] { " ", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" })
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  176 */           boolean[] canEdit = new boolean[] { 
/*      */               false, false, false, false, false, false, false, false, false, false, 
/*      */               false, false, false };
/*      */           
/*      */           public boolean isCellEditable(int rowIndex, int columnIndex) {
/*  181 */             return this.canEdit[columnIndex];
/*      */           }
/*      */         });
/*  184 */     this.rainfallTable1.setCursor(new Cursor(0));
/*  185 */     this.datasetScrollPane2.setViewportView(this.rainfallTable1);
/*      */     
/*  187 */     setDefaultCloseOperation(3);
/*      */     
/*  189 */     this.datasetPanel.setBorder(BorderFactory.createTitledBorder(null, "Dataset", 0, 0, new Font("SansSerif", 0, 11)));
/*  190 */     this.datasetPanel.setFont(this.datasetPanel.getFont());
/*      */     
/*  192 */     this.rainfallTable.setFont(this.rainfallTable.getFont());
/*  193 */     this.rainfallTable.setModel(new DefaultTableModel(new Object[][] { { "", null, null, null, null, null, null, null, null, null, null, null, null },  }, (Object[])new String[] { " ", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" })
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  201 */           boolean[] canEdit = new boolean[] { 
/*      */               false, false, false, false, false, false, false, false, false, false, 
/*      */               false, false, false };
/*      */           
/*      */           public boolean isCellEditable(int rowIndex, int columnIndex) {
/*  206 */             return this.canEdit[columnIndex];
/*      */           }
/*      */         });
/*  209 */     this.rainfallTable.setCursor(new Cursor(0));
/*  210 */     this.datasetScrollPane.setViewportView(this.rainfallTable);
/*      */     
/*  212 */     this.tempTable.setFont(this.tempTable.getFont());
/*  213 */     this.tempTable.setModel(new DefaultTableModel(new Object[][] { { "", null, null, null, null, null, null, null, null, null, null, null, null },  }, (Object[])new String[] { " ", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" })
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  221 */           boolean[] canEdit = new boolean[] { 
/*      */               false, false, false, false, false, false, false, false, false, false, 
/*      */               false, false, false };
/*      */           
/*      */           public boolean isCellEditable(int rowIndex, int columnIndex) {
/*  226 */             return this.canEdit[columnIndex];
/*      */           }
/*      */         });
/*  229 */     this.tempTable.setCursor(new Cursor(0));
/*  230 */     this.datasetScrollPane3.setViewportView(this.tempTable);
/*      */     
/*  232 */     GroupLayout datasetPanelLayout = new GroupLayout(this.datasetPanel);
/*  233 */     this.datasetPanel.setLayout(datasetPanelLayout);
/*  234 */     datasetPanelLayout.setHorizontalGroup(datasetPanelLayout
/*  235 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  236 */         .addGroup(GroupLayout.Alignment.TRAILING, datasetPanelLayout.createSequentialGroup()
/*  237 */           .addContainerGap()
/*  238 */           .addGroup(datasetPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/*  239 */             .addComponent(this.datasetScrollPane3, GroupLayout.Alignment.LEADING, -1, 895, 32767)
/*  240 */             .addComponent(this.datasetScrollPane, GroupLayout.Alignment.LEADING, -1, 895, 32767))
/*  241 */           .addContainerGap()));
/*      */     
/*  243 */     datasetPanelLayout.setVerticalGroup(datasetPanelLayout
/*  244 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  245 */         .addGroup(GroupLayout.Alignment.TRAILING, datasetPanelLayout.createSequentialGroup()
/*  246 */           .addContainerGap()
/*  247 */           .addComponent(this.datasetScrollPane, -1, 85, 32767)
/*  248 */           .addGap(18, 18, 18)
/*  249 */           .addComponent(this.datasetScrollPane3, -1, 86, 32767)
/*  250 */           .addContainerGap()));
/*      */ 
/*      */     
/*  253 */     this.stationLabel.setFont(this.stationLabel.getFont());
/*  254 */     this.stationLabel.setText("Station:");
/*      */     
/*  256 */     this.stationText.setFont(this.stationText.getFont());
/*  257 */     this.stationText.setText(" << No Dataset Loaded >>");
/*      */     
/*  259 */     this.elevationLabel.setFont(this.elevationLabel.getFont());
/*  260 */     this.elevationLabel.setText("Elevation:");
/*      */     
/*  262 */     this.elevationText.setFont(this.elevationText.getFont());
/*  263 */     this.elevationText.setText(" ");
/*      */     
/*  265 */     this.latitudeLabel.setFont(this.latitudeLabel.getFont());
/*  266 */     this.latitudeLabel.setText("Latitude:");
/*      */     
/*  268 */     this.latitudeText.setFont(this.latitudeText.getFont());
/*  269 */     this.latitudeText.setText(" ");
/*      */     
/*  271 */     this.longitudeLabel.setFont(this.longitudeLabel.getFont());
/*  272 */     this.longitudeLabel.setText("Longitude:");
/*      */     
/*  274 */     this.longitudeText.setFont(this.longitudeText.getFont());
/*  275 */     this.longitudeText.setText(" ");
/*      */     
/*  277 */     this.countryLabel.setText("Station Country:");
/*      */     
/*  279 */     this.countryText.setText(" ");
/*      */     
/*  281 */     this.startYearLabel.setText("Starting Year:");
/*      */     
/*  283 */     this.startingYearText.setText(" ");
/*      */     
/*  285 */     this.endingYearLabel.setText("Ending Year:");
/*      */     
/*  287 */     this.endingYearText.setText(" ");
/*      */     
/*  289 */     this.srcUnitSystemLabel.setText("Source Unit System:");
/*      */     
/*  291 */     this.srcUnitSystem.setText(" ");
/*      */     
/*  293 */     this.soilAirOffsetLabel.setText("Soil-Air Temp Offset:");
/*      */     
/*  295 */     this.soilAirOffset.setModel(new SpinnerNumberModel(1.2D, 0.0D, 20.0D, 0.1D));
/*  296 */     this.soilAirOffset.setEnabled(false);
/*  297 */     this.soilAirOffset.setVerifyInputWhenFocusTarget(false);
/*  298 */     this.soilAirOffset.addChangeListener(new ChangeListener() {
/*      */           public void stateChanged(ChangeEvent evt) {
/*  300 */             DefaultNewhallFrame.this.soilAirOffsetStateChanged(evt);
/*      */           }
/*      */         });
/*      */     
/*  304 */     this.soilAirOffsetUnits.setText("°C");
/*      */     
/*  306 */     this.jLabel21.setText("Amplitude:");
/*      */     
/*  308 */     this.jLabel23.setText("greater than air temperature");
/*      */     
/*  310 */     this.amplitudeSpinner.setModel(new SpinnerNumberModel(0.66D, 0.0D, 1.0D, 0.01D));
/*  311 */     this.amplitudeSpinner.setEnabled(false);
/*  312 */     this.amplitudeSpinner.addChangeListener(new ChangeListener() {
/*      */           public void stateChanged(ChangeEvent evt) {
/*  314 */             DefaultNewhallFrame.this.amplitudeSpinnerStateChanged(evt);
/*      */           }
/*      */         });
/*      */     
/*  318 */     GroupLayout inputPanelLayout = new GroupLayout(this.inputPanel);
/*  319 */     this.inputPanel.setLayout(inputPanelLayout);
/*  320 */     inputPanelLayout.setHorizontalGroup(inputPanelLayout
/*  321 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  322 */         .addGroup(inputPanelLayout.createSequentialGroup()
/*  323 */           .addContainerGap()
/*  324 */           .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  325 */             .addGroup(inputPanelLayout.createSequentialGroup()
/*  326 */               .addComponent(this.datasetPanel, -1, -1, 32767)
/*  327 */               .addContainerGap())
/*  328 */             .addGroup(inputPanelLayout.createSequentialGroup()
/*  329 */               .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  330 */                 .addComponent(this.countryLabel)
/*  331 */                 .addComponent(this.stationLabel)
/*  332 */                 .addComponent(this.elevationLabel)
/*  333 */                 .addComponent(this.latitudeLabel)
/*  334 */                 .addComponent(this.longitudeLabel))
/*  335 */               .addGap(18, 18, 18)
/*  336 */               .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
/*  337 */                 .addComponent(this.longitudeText, -1, -1, 32767)
/*  338 */                 .addComponent(this.latitudeText, -1, -1, 32767)
/*  339 */                 .addComponent(this.elevationText, -1, -1, 32767)
/*  340 */                 .addComponent(this.stationText, -1, -1, 32767)
/*  341 */                 .addComponent(this.countryText, -1, 191, 32767))
/*  342 */               .addGap(18, 223, 32767)
/*  343 */               .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  344 */                 .addComponent(this.srcUnitSystemLabel)
/*  345 */                 .addComponent(this.endingYearLabel)
/*  346 */                 .addComponent(this.startYearLabel))
/*  347 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  348 */               .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  349 */                 .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
/*  350 */                   .addComponent(this.startingYearText, -1, -1, 32767)
/*  351 */                   .addComponent(this.endingYearText, -2, 49, -2))
/*  352 */                 .addComponent(this.srcUnitSystem, -2, 86, -2))
/*  353 */               .addGap(167, 167, 167))
/*  354 */             .addGroup(inputPanelLayout.createSequentialGroup()
/*  355 */               .addComponent(this.jSeparator2, -1, 931, 32767)
/*  356 */               .addContainerGap())
/*  357 */             .addGroup(inputPanelLayout.createSequentialGroup()
/*  358 */               .addComponent(this.soilAirOffsetLabel)
/*  359 */               .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/*  360 */               .addComponent(this.soilAirOffset, -2, 57, -2)
/*  361 */               .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/*  362 */               .addComponent(this.soilAirOffsetUnits)
/*  363 */               .addGap(5, 5, 5)
/*  364 */               .addComponent(this.jLabel23)
/*  365 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 257, 32767)
/*  366 */               .addComponent(this.jLabel21)
/*  367 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  368 */               .addComponent(this.amplitudeSpinner, -2, 60, -2)
/*  369 */               .addContainerGap())
/*  370 */             .addGroup(inputPanelLayout.createSequentialGroup()
/*  371 */               .addComponent(this.jSeparator3, -1, 931, 32767)
/*  372 */               .addContainerGap()))));
/*      */     
/*  374 */     inputPanelLayout.setVerticalGroup(inputPanelLayout
/*  375 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  376 */         .addGroup(GroupLayout.Alignment.TRAILING, inputPanelLayout.createSequentialGroup()
/*  377 */           .addContainerGap()
/*  378 */           .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  379 */             .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/*  380 */               .addGroup(inputPanelLayout.createSequentialGroup()
/*  381 */                 .addComponent(this.stationLabel, -2, 14, -2)
/*  382 */                 .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  383 */                 .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  384 */                   .addComponent(this.countryLabel)
/*  385 */                   .addComponent(this.countryText)))
/*  386 */               .addGroup(inputPanelLayout.createSequentialGroup()
/*  387 */                 .addComponent(this.stationText)
/*  388 */                 .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  389 */                 .addComponent(this.endingYearLabel)))
/*  390 */             .addGroup(inputPanelLayout.createSequentialGroup()
/*  391 */               .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  392 */                 .addComponent(this.startingYearText)
/*  393 */                 .addComponent(this.startYearLabel))
/*  394 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  395 */               .addComponent(this.endingYearText)))
/*  396 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  397 */           .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  398 */             .addGroup(inputPanelLayout.createSequentialGroup()
/*  399 */               .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  400 */                 .addComponent(this.elevationLabel, -1, -1, 32767)
/*  401 */                 .addComponent(this.elevationText, -1, -1, 32767)
/*  402 */                 .addComponent(this.srcUnitSystemLabel))
/*  403 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  404 */               .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  405 */                 .addComponent(this.latitudeLabel)
/*  406 */                 .addComponent(this.latitudeText, -2, 14, -2))
/*  407 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  408 */               .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  409 */                 .addComponent(this.longitudeLabel, -1, -1, 32767)
/*  410 */                 .addComponent(this.longitudeText, -2, 14, -2)))
/*  411 */             .addComponent(this.srcUnitSystem))
/*  412 */           .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/*  413 */           .addComponent(this.jSeparator2, -2, 10, -2)
/*  414 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  415 */           .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  416 */             .addComponent(this.soilAirOffsetLabel)
/*  417 */             .addComponent(this.soilAirOffset, -2, -1, -2)
/*  418 */             .addComponent(this.soilAirOffsetUnits)
/*  419 */             .addComponent(this.jLabel23)
/*  420 */             .addComponent(this.jLabel21)
/*  421 */             .addComponent(this.amplitudeSpinner, -2, -1, -2))
/*  422 */           .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/*  423 */           .addComponent(this.jSeparator3, -2, 10, -2)
/*  424 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  425 */           .addComponent(this.datasetPanel, -2, -1, -2)
/*  426 */           .addContainerGap()));
/*      */ 
/*      */     
/*  429 */     this.tabPane.addTab("Input", this.inputPanel);
/*      */     
/*  431 */     this.tempCalPanel.setBorder(BorderFactory.createTitledBorder("Temperature Calendar"));
/*      */     
/*  433 */     this.jTextArea3.setBackground(new Color(0, 0, 0));
/*  434 */     this.jTextArea3.setColumns(30);
/*  435 */     this.jTextArea3.setFont(new Font("Monospaced", 0, 14));
/*  436 */     this.jTextArea3.setForeground(new Color(255, 255, 255));
/*  437 */     this.jTextArea3.setRows(1);
/*  438 */     this.jTextArea3.setText("1''''''''''''15'''''''''''''30");
/*  439 */     this.jTextArea3.setBorder((Border)null);
/*  440 */     this.jScrollPane4.setViewportView(this.jTextArea3);
/*      */     
/*  442 */     this.jTextArea4.setBackground(new Color(0, 0, 0));
/*  443 */     this.jTextArea4.setColumns(5);
/*  444 */     this.jTextArea4.setFont(new Font("Monospaced", 0, 14));
/*  445 */     this.jTextArea4.setForeground(new Color(255, 255, 255));
/*  446 */     this.jTextArea4.setRows(12);
/*  447 */     this.jTextArea4.setText(" JAN\n FEB\n MAR\n APR\n MAY\n JUN\n JUL\n AUG\n SEP\n OCT\n NOV\n DEC");
/*  448 */     this.jTextArea4.setBorder((Border)null);
/*  449 */     this.jScrollPane5.setViewportView(this.jTextArea4);
/*      */     
/*  451 */     this.temperatureCalendarText.setBackground(new Color(0, 0, 0));
/*  452 */     this.temperatureCalendarText.setColumns(30);
/*  453 */     this.temperatureCalendarText.setEditable(false);
/*  454 */     this.temperatureCalendarText.setFont(new Font("Monospaced", 0, 14));
/*  455 */     this.temperatureCalendarText.setForeground(new Color(255, 255, 255));
/*  456 */     this.temperatureCalendarText.setRows(12);
/*  457 */     this.temperatureCalendarText.setBorder((Border)null);
/*  458 */     this.jScrollPane6.setViewportView(this.temperatureCalendarText);
/*      */     
/*  460 */     this.jLabel8.setFont(new Font("SansSerif", 0, 11));
/*  461 */     this.jLabel8.setText("- = Under 5°C, 5 = 5°C to 8°C, 8 = Excess of 8°C");
/*      */ 
/*      */ 
/*      */     
/*  465 */     GroupLayout tempCalPanelLayout = new GroupLayout(this.tempCalPanel);
/*  466 */     this.tempCalPanel.setLayout(tempCalPanelLayout);
/*  467 */     tempCalPanelLayout.setHorizontalGroup(tempCalPanelLayout
/*  468 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  469 */         .addGroup(tempCalPanelLayout.createSequentialGroup()
/*  470 */           .addContainerGap()
/*  471 */           .addGroup(tempCalPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  472 */             .addGroup(tempCalPanelLayout.createSequentialGroup()
/*  473 */               .addComponent(this.jScrollPane5, -2, -1, -2)
/*  474 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  475 */               .addGroup(tempCalPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  476 */                 .addComponent(this.jScrollPane6)
/*  477 */                 .addComponent(this.jScrollPane4, -2, -1, -2))
/*  478 */               .addGap(9, 9, 9))
/*  479 */             .addGroup(GroupLayout.Alignment.TRAILING, tempCalPanelLayout.createSequentialGroup()
/*  480 */               .addComponent(this.jLabel8)
/*  481 */               .addContainerGap()))));
/*      */     
/*  483 */     tempCalPanelLayout.setVerticalGroup(tempCalPanelLayout
/*  484 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  485 */         .addGroup(tempCalPanelLayout.createSequentialGroup()
/*  486 */           .addComponent(this.jScrollPane4, -2, -1, -2)
/*  487 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  488 */           .addGroup(tempCalPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
/*  489 */             .addComponent(this.jScrollPane6)
/*  490 */             .addComponent(this.jScrollPane5))
/*  491 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  492 */           .addComponent(this.jLabel8)));
/*      */ 
/*      */     
/*  495 */     this.moistCalPanel.setBorder(BorderFactory.createTitledBorder("Moisture Calendar"));
/*      */     
/*  497 */     this.jTextArea1.setBackground(new Color(0, 0, 0));
/*  498 */     this.jTextArea1.setColumns(30);
/*  499 */     this.jTextArea1.setFont(new Font("Monospaced", 0, 14));
/*  500 */     this.jTextArea1.setForeground(new Color(255, 255, 255));
/*  501 */     this.jTextArea1.setRows(1);
/*  502 */     this.jTextArea1.setText("1''''''''''''15'''''''''''''30");
/*  503 */     this.jTextArea1.setBorder((Border)null);
/*  504 */     this.jScrollPane2.setViewportView(this.jTextArea1);
/*      */     
/*  506 */     this.jTextArea2.setBackground(new Color(0, 0, 0));
/*  507 */     this.jTextArea2.setColumns(5);
/*  508 */     this.jTextArea2.setFont(new Font("Monospaced", 0, 14));
/*  509 */     this.jTextArea2.setForeground(new Color(255, 255, 255));
/*  510 */     this.jTextArea2.setRows(12);
/*  511 */     this.jTextArea2.setText(" JAN\n FEB\n MAR\n APR\n MAY\n JUN\n JUL\n AUG\n SEP\n OCT\n NOV\n DEC");
/*  512 */     this.jTextArea2.setBorder((Border)null);
/*  513 */     this.jScrollPane3.setViewportView(this.jTextArea2);
/*      */     
/*  515 */     this.moistureCalendarText.setBackground(new Color(0, 0, 0));
/*  516 */     this.moistureCalendarText.setColumns(30);
/*  517 */     this.moistureCalendarText.setEditable(false);
/*  518 */     this.moistureCalendarText.setFont(new Font("Monospaced", 0, 14));
/*  519 */     this.moistureCalendarText.setForeground(new Color(255, 255, 255));
/*  520 */     this.moistureCalendarText.setRows(12);
/*  521 */     this.moistureCalendarText.setBorder((Border)null);
/*  522 */     this.jScrollPane1.setViewportView(this.moistureCalendarText);
/*      */     
/*  524 */     this.jLabel7.setFont(new Font("SansSerif", 0, 11));
/*  525 */     this.jLabel7.setText("1 = Dry, 2 = Moist/Dry, 3 = Moist");
/*      */     
/*  527 */     GroupLayout moistCalPanelLayout = new GroupLayout(this.moistCalPanel);
/*  528 */     this.moistCalPanel.setLayout(moistCalPanelLayout);
/*  529 */     moistCalPanelLayout.setHorizontalGroup(moistCalPanelLayout
/*  530 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  531 */         .addGroup(moistCalPanelLayout.createSequentialGroup()
/*  532 */           .addContainerGap()
/*  533 */           .addGroup(moistCalPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  534 */             .addGroup(moistCalPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  535 */               .addGroup(moistCalPanelLayout.createSequentialGroup()
/*  536 */                 .addComponent(this.jScrollPane3, -2, -1, -2)
/*  537 */                 .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  538 */                 .addComponent(this.jScrollPane1, -2, -1, -2)
/*  539 */                 .addContainerGap())
/*  540 */               .addGroup(GroupLayout.Alignment.TRAILING, moistCalPanelLayout.createSequentialGroup()
/*  541 */                 .addComponent(this.jScrollPane2, -2, -1, -2)
/*  542 */                 .addContainerGap()))
/*  543 */             .addGroup(GroupLayout.Alignment.TRAILING, moistCalPanelLayout.createSequentialGroup()
/*  544 */               .addComponent(this.jLabel7)
/*  545 */               .addContainerGap()))));
/*      */     
/*  547 */     moistCalPanelLayout.setVerticalGroup(moistCalPanelLayout
/*  548 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  549 */         .addGroup(moistCalPanelLayout.createSequentialGroup()
/*  550 */           .addComponent(this.jScrollPane2, -2, -1, -2)
/*  551 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  552 */           .addGroup(moistCalPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
/*  553 */             .addComponent(this.jScrollPane1)
/*  554 */             .addComponent(this.jScrollPane3))
/*  555 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
/*  556 */           .addComponent(this.jLabel7, -2, 17, -2)));
/*      */ 
/*      */     
/*  559 */     GroupLayout calendarPanelLayout = new GroupLayout(this.calendarPanel);
/*  560 */     this.calendarPanel.setLayout(calendarPanelLayout);
/*  561 */     calendarPanelLayout.setHorizontalGroup(calendarPanelLayout
/*  562 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  563 */         .addGroup(GroupLayout.Alignment.TRAILING, calendarPanelLayout.createSequentialGroup()
/*  564 */           .addContainerGap()
/*  565 */           .addComponent(this.tempCalPanel, -2, -1, -2)
/*  566 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 282, 32767)
/*  567 */           .addComponent(this.moistCalPanel, -2, -1, -2)
/*  568 */           .addContainerGap()));
/*      */     
/*  570 */     calendarPanelLayout.setVerticalGroup(calendarPanelLayout
/*  571 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  572 */         .addGroup(calendarPanelLayout.createSequentialGroup()
/*  573 */           .addGroup(calendarPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/*  574 */             .addGroup(calendarPanelLayout.createSequentialGroup()
/*  575 */               .addContainerGap()
/*  576 */               .addComponent(this.tempCalPanel, -2, -1, -2))
/*  577 */             .addGroup(GroupLayout.Alignment.LEADING, calendarPanelLayout.createSequentialGroup()
/*  578 */               .addContainerGap()
/*  579 */               .addComponent(this.moistCalPanel, -2, -1, -2)))
/*  580 */           .addContainerGap(129, 32767)));
/*      */ 
/*      */     
/*  583 */     this.tabPane.addTab("Calendars", this.calendarPanel);
/*      */     
/*  585 */     this.modelResultsPanel.setBorder(BorderFactory.createTitledBorder("Model Results"));
/*      */     
/*  587 */     this.jLabel3.setText("Annual Rainfall:");
/*      */     
/*  589 */     this.jLabel4.setText("Temperature Regime:");
/*      */     
/*  591 */     this.jLabel5.setText("Moisture Regime:");
/*      */     
/*  593 */     this.mpeTable.setFont(this.mpeTable.getFont());
/*  594 */     this.mpeTable.setModel(new DefaultTableModel(new Object[][] { { "", null, null, null, null, null, null, null, null, null, null, null, null },  }, (Object[])new String[] { " ", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" })
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  602 */           boolean[] canEdit = new boolean[] { 
/*      */               false, false, false, false, false, false, false, false, false, false, 
/*      */               false, false, false };
/*      */           
/*      */           public boolean isCellEditable(int rowIndex, int columnIndex) {
/*  607 */             return this.canEdit[columnIndex];
/*      */           }
/*      */         });
/*  610 */     this.mpeTable.setCursor(new Cursor(0));
/*  611 */     this.datasetScrollPane1.setViewportView(this.mpeTable);
/*      */     
/*  613 */     this.moistureRegimeText.setText(" ");
/*      */     
/*  615 */     this.temperatureRegimeText.setText(" ");
/*      */     
/*  617 */     this.annualRainfallText.setText(" ");
/*      */     
/*  619 */     this.jPanel3.setBorder(BorderFactory.createTitledBorder("Extended Statistics"));
/*      */     
/*  621 */     this.statisticsText.setBackground(new Color(0, 0, 0));
/*  622 */     this.statisticsText.setColumns(20);
/*  623 */     this.statisticsText.setEditable(false);
/*  624 */     this.statisticsText.setFont(new Font("Monospaced", 0, 11));
/*  625 */     this.statisticsText.setForeground(new Color(255, 255, 255));
/*  626 */     this.statisticsText.setRows(15);
/*  627 */     this.jScrollPane7.setViewportView(this.statisticsText);
/*      */     
/*  629 */     GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
/*  630 */     this.jPanel3.setLayout(jPanel3Layout);
/*  631 */     jPanel3Layout.setHorizontalGroup(jPanel3Layout
/*  632 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  633 */         .addGroup(jPanel3Layout.createSequentialGroup()
/*  634 */           .addContainerGap()
/*  635 */           .addComponent(this.jScrollPane7, -1, 883, 32767)
/*  636 */           .addContainerGap()));
/*      */     
/*  638 */     jPanel3Layout.setVerticalGroup(jPanel3Layout
/*  639 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  640 */         .addGroup(jPanel3Layout.createSequentialGroup()
/*  641 */           .addComponent(this.jScrollPane7, -1, 269, 32767)
/*  642 */           .addContainerGap()));
/*      */ 
/*      */     
/*  645 */     this.subdivisionsLabel.setText("Subdivisions:");
/*      */     
/*  647 */     this.subdivisionsText.setText(" ");
/*      */     
/*  649 */     GroupLayout modelResultsPanelLayout = new GroupLayout(this.modelResultsPanel);
/*  650 */     this.modelResultsPanel.setLayout(modelResultsPanelLayout);
/*  651 */     modelResultsPanelLayout.setHorizontalGroup(modelResultsPanelLayout
/*  652 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  653 */         .addGroup(GroupLayout.Alignment.TRAILING, modelResultsPanelLayout.createSequentialGroup()
/*  654 */           .addContainerGap()
/*  655 */           .addGroup(modelResultsPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/*  656 */             .addComponent(this.jPanel3, GroupLayout.Alignment.LEADING, -1, -1, 32767)
/*  657 */             .addComponent(this.datasetScrollPane1, GroupLayout.Alignment.LEADING, -1, 919, 32767)
/*  658 */             .addGroup(GroupLayout.Alignment.LEADING, modelResultsPanelLayout.createSequentialGroup()
/*  659 */               .addComponent(this.jLabel3)
/*  660 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  661 */               .addComponent(this.annualRainfallText, -2, 78, -2)
/*  662 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  663 */               .addComponent(this.jLabel4)
/*  664 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  665 */               .addComponent(this.temperatureRegimeText, -2, 125, -2)
/*  666 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  667 */               .addComponent(this.jLabel5)
/*  668 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  669 */               .addComponent(this.moistureRegimeText, -2, 92, -2)
/*  670 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  671 */               .addComponent(this.subdivisionsLabel)
/*  672 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  673 */               .addComponent(this.subdivisionsText, -1, 113, 32767)))
/*  674 */           .addContainerGap()));
/*      */     
/*  676 */     modelResultsPanelLayout.setVerticalGroup(modelResultsPanelLayout
/*  677 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  678 */         .addGroup(modelResultsPanelLayout.createSequentialGroup()
/*  679 */           .addGroup(modelResultsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  680 */             .addComponent(this.jLabel3)
/*  681 */             .addComponent(this.temperatureRegimeText)
/*  682 */             .addComponent(this.annualRainfallText)
/*  683 */             .addComponent(this.subdivisionsText)
/*  684 */             .addComponent(this.jLabel5)
/*  685 */             .addComponent(this.moistureRegimeText)
/*  686 */             .addComponent(this.subdivisionsLabel)
/*  687 */             .addComponent(this.jLabel4))
/*  688 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  689 */           .addComponent(this.datasetScrollPane1, -2, 45, -2)
/*  690 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  691 */           .addComponent(this.jPanel3, -1, -1, 32767)
/*  692 */           .addContainerGap()));
/*      */ 
/*      */     
/*  695 */     this.tabPane.addTab("Results", this.modelResultsPanel);
/*      */     
/*  697 */     this.stationNameLabel.setText("Station Name:");
/*      */     
/*  699 */     this.jLabel1.setText("Station ID:");
/*      */     
/*  701 */     this.jLabel2.setText("Elevation:");
/*      */     
/*  703 */     this.jLabel9.setText("State/Prov:");
/*      */     
/*  705 */     this.jLabel10.setText("Country:");
/*      */     
/*  707 */     this.jLabel11.setText("MLRA Name:");
/*      */     
/*  709 */     this.jLabel12.setText("MLRA ID:");
/*      */     
/*  711 */     this.contributorPanel.setBorder(BorderFactory.createTitledBorder("Contributor"));
/*      */     
/*  713 */     this.jLabel13.setText("First Name:");
/*      */     
/*  715 */     this.jLabel14.setText("Last Name:");
/*      */     
/*  717 */     this.jLabel15.setText("Title:");
/*      */     
/*  719 */     this.jLabel16.setText("Org:");
/*      */     
/*  721 */     this.jLabel17.setText("Address:");
/*      */     
/*  723 */     this.jLabel18.setText("City:");
/*      */     
/*  725 */     this.stateProvLabel.setText("State/Prov:");
/*      */     
/*  727 */     this.postalLabel.setText("Postal:");
/*      */     
/*  729 */     this.contribCountryLabel.setText("Country:");
/*      */     
/*  731 */     this.emailLabel.setText("Email:");
/*      */     
/*  733 */     this.phoneLabel.setText("Phone:");
/*      */     
/*  735 */     this.firstName.setText(" ");
/*      */     
/*  737 */     this.lastName.setText(" ");
/*      */     
/*  739 */     this.title.setText(" ");
/*      */     
/*  741 */     this.orginization.setText(" ");
/*      */     
/*  743 */     this.address.setText("  ");
/*      */     
/*  745 */     this.city.setText(" ");
/*      */     
/*  747 */     this.stateProv.setText(" ");
/*      */     
/*  749 */     this.postal.setText(" ");
/*      */     
/*  751 */     this.country.setText(" ");
/*      */     
/*  753 */     this.email.setText(" ");
/*      */     
/*  755 */     this.phone.setText(" ");
/*      */     
/*  757 */     GroupLayout contributorPanelLayout = new GroupLayout(this.contributorPanel);
/*  758 */     this.contributorPanel.setLayout(contributorPanelLayout);
/*  759 */     contributorPanelLayout.setHorizontalGroup(contributorPanelLayout
/*  760 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  761 */         .addGroup(contributorPanelLayout.createSequentialGroup()
/*  762 */           .addContainerGap()
/*  763 */           .addGroup(contributorPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/*  764 */             .addComponent(this.phoneLabel)
/*  765 */             .addComponent(this.emailLabel)
/*  766 */             .addComponent(this.contribCountryLabel)
/*  767 */             .addComponent(this.postalLabel)
/*  768 */             .addComponent(this.stateProvLabel)
/*  769 */             .addComponent(this.jLabel18)
/*  770 */             .addComponent(this.jLabel15)
/*  771 */             .addComponent(this.jLabel14)
/*  772 */             .addComponent(this.jLabel13)
/*  773 */             .addComponent(this.jLabel16)
/*  774 */             .addComponent(this.jLabel17))
/*  775 */           .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/*  776 */           .addGroup(contributorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  777 */             .addComponent(this.firstName)
/*  778 */             .addComponent(this.lastName)
/*  779 */             .addComponent(this.title)
/*  780 */             .addComponent(this.orginization)
/*  781 */             .addComponent(this.address)
/*  782 */             .addComponent(this.city)
/*  783 */             .addComponent(this.stateProv)
/*  784 */             .addComponent(this.postal)
/*  785 */             .addComponent(this.country)
/*  786 */             .addComponent(this.email)
/*  787 */             .addComponent(this.phone))
/*  788 */           .addContainerGap(164, 32767)));
/*      */     
/*  790 */     contributorPanelLayout.setVerticalGroup(contributorPanelLayout
/*  791 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  792 */         .addGroup(contributorPanelLayout.createSequentialGroup()
/*  793 */           .addGroup(contributorPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  794 */             .addComponent(this.jLabel13)
/*  795 */             .addComponent(this.firstName))
/*  796 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  797 */           .addGroup(contributorPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  798 */             .addComponent(this.jLabel14)
/*  799 */             .addComponent(this.lastName))
/*  800 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  801 */           .addGroup(contributorPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  802 */             .addComponent(this.jLabel15)
/*  803 */             .addComponent(this.title))
/*  804 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  805 */           .addGroup(contributorPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  806 */             .addComponent(this.jLabel16)
/*  807 */             .addComponent(this.orginization))
/*  808 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  809 */           .addGroup(contributorPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  810 */             .addComponent(this.jLabel17)
/*  811 */             .addComponent(this.address))
/*  812 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  813 */           .addGroup(contributorPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  814 */             .addComponent(this.jLabel18)
/*  815 */             .addComponent(this.city))
/*  816 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  817 */           .addGroup(contributorPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  818 */             .addComponent(this.stateProvLabel)
/*  819 */             .addComponent(this.stateProv))
/*  820 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  821 */           .addGroup(contributorPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  822 */             .addComponent(this.postalLabel)
/*  823 */             .addComponent(this.postal))
/*  824 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  825 */           .addGroup(contributorPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  826 */             .addComponent(this.contribCountryLabel)
/*  827 */             .addComponent(this.country))
/*  828 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  829 */           .addGroup(contributorPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  830 */             .addComponent(this.emailLabel)
/*  831 */             .addComponent(this.email))
/*  832 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  833 */           .addGroup(contributorPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  834 */             .addComponent(this.phoneLabel)
/*  835 */             .addComponent(this.phone))
/*  836 */           .addContainerGap(113, 32767)));
/*      */ 
/*      */     
/*  839 */     this.notesPanel.setBorder(BorderFactory.createTitledBorder("Notes"));
/*      */     
/*  841 */     this.notes.setColumns(20);
/*  842 */     this.notes.setEditable(false);
/*  843 */     this.notes.setRows(5);
/*  844 */     this.jScrollPane8.setViewportView(this.notes);
/*      */     
/*  846 */     GroupLayout notesPanelLayout = new GroupLayout(this.notesPanel);
/*  847 */     this.notesPanel.setLayout(notesPanelLayout);
/*  848 */     notesPanelLayout.setHorizontalGroup(notesPanelLayout
/*  849 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  850 */         .addGroup(notesPanelLayout.createSequentialGroup()
/*  851 */           .addContainerGap()
/*  852 */           .addComponent(this.jScrollPane8, -1, 602, 32767)
/*  853 */           .addContainerGap()));
/*      */     
/*  855 */     notesPanelLayout.setVerticalGroup(notesPanelLayout
/*  856 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  857 */         .addGroup(notesPanelLayout.createSequentialGroup()
/*  858 */           .addComponent(this.jScrollPane8, -1, 191, 32767)
/*  859 */           .addContainerGap()));
/*      */ 
/*      */     
/*  862 */     this.stationName.setText(" ");
/*      */     
/*  864 */     this.stationId.setText(" ");
/*      */     
/*  866 */     this.elevation.setText(" ");
/*      */     
/*  868 */     this.stationStateProv.setText(" ");
/*      */     
/*  870 */     this.stationCountry.setText(" ");
/*      */     
/*  872 */     this.mlraName.setText(" ");
/*      */     
/*  874 */     this.mlraId.setText(" ");
/*      */     
/*  876 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/*  877 */     this.jPanel1.setLayout(jPanel1Layout);
/*  878 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout
/*  879 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  880 */         .addGroup(jPanel1Layout.createSequentialGroup()
/*  881 */           .addContainerGap()
/*  882 */           .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  883 */             .addGroup(jPanel1Layout.createSequentialGroup()
/*  884 */               .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/*  885 */                 .addComponent(this.jLabel12)
/*  886 */                 .addComponent(this.jLabel11)
/*  887 */                 .addComponent(this.jLabel10)
/*  888 */                 .addComponent(this.jLabel9)
/*  889 */                 .addComponent(this.jLabel2)
/*  890 */                 .addComponent(this.jLabel1)
/*  891 */                 .addComponent(this.stationNameLabel))
/*  892 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  893 */               .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  894 */                 .addComponent(this.mlraId)
/*  895 */                 .addComponent(this.mlraName)
/*  896 */                 .addComponent(this.stationCountry)
/*  897 */                 .addComponent(this.stationStateProv)
/*  898 */                 .addComponent(this.elevation)
/*  899 */                 .addComponent(this.stationId)
/*  900 */                 .addComponent(this.stationName)))
/*  901 */             .addComponent(this.notesPanel, -1, -1, 32767))
/*  902 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  903 */           .addComponent(this.contributorPanel, -2, -1, -2)
/*  904 */           .addContainerGap()));
/*      */     
/*  906 */     jPanel1Layout.setVerticalGroup(jPanel1Layout
/*  907 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  908 */         .addGroup(jPanel1Layout.createSequentialGroup()
/*  909 */           .addContainerGap()
/*  910 */           .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  911 */             .addComponent(this.contributorPanel, -1, -1, 32767)
/*  912 */             .addGroup(jPanel1Layout.createSequentialGroup()
/*  913 */               .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  914 */                 .addComponent(this.stationNameLabel)
/*  915 */                 .addComponent(this.stationName))
/*  916 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  917 */               .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  918 */                 .addComponent(this.jLabel1)
/*  919 */                 .addComponent(this.stationId))
/*  920 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  921 */               .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  922 */                 .addComponent(this.jLabel2)
/*  923 */                 .addComponent(this.elevation))
/*  924 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  925 */               .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  926 */                 .addComponent(this.jLabel9)
/*  927 */                 .addComponent(this.stationStateProv))
/*  928 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  929 */               .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  930 */                 .addComponent(this.jLabel10)
/*  931 */                 .addComponent(this.stationCountry))
/*  932 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  933 */               .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  934 */                 .addComponent(this.jLabel11)
/*  935 */                 .addComponent(this.mlraName))
/*  936 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  937 */               .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  938 */                 .addComponent(this.jLabel12)
/*  939 */                 .addComponent(this.mlraId))
/*  940 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  941 */               .addComponent(this.notesPanel, -1, -1, 32767)))
/*  942 */           .addContainerGap()));
/*      */ 
/*      */     
/*  945 */     this.tabPane.addTab("Metadata", this.jPanel1);
/*      */     
/*  947 */     this.jLabel6.setText("Waterholding Capacity:");
/*      */     
/*  949 */     this.whcSpinner.setModel(new SpinnerNumberModel(Double.valueOf(200.0D), Double.valueOf(0.0D), null, Double.valueOf(0.1D)));
/*  950 */     this.whcSpinner.addChangeListener(new ChangeListener() {
/*      */           public void stateChanged(ChangeEvent evt) {
/*  952 */             DefaultNewhallFrame.this.whcSpinnerStateChanged(evt);
/*      */           }
/*      */         });
/*      */     
/*  956 */     this.whcUnitsText.setText("mm");
/*      */     
/*  958 */     this.exportXmlButton.setText("Export to XML");
/*  959 */     this.exportXmlButton.setEnabled(false);
/*  960 */     this.exportXmlButton.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent evt) {
/*  962 */             DefaultNewhallFrame.this.exportXmlButtonActionPerformed(evt);
/*      */           }
/*      */         });
/*      */     
/*  966 */     this.exportCsvButton.setText("Export to FLX");
/*  967 */     this.exportCsvButton.setEnabled(false);
/*  968 */     this.exportCsvButton.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent evt) {
/*  970 */             DefaultNewhallFrame.this.exportCsvButtonActionPerformed(evt);
/*      */           }
/*      */         });
/*      */     
/*  974 */     this.activeUnitSystemLabel.setText("Display Unit System:");
/*      */     
/*  976 */     this.activeUnitSystemText.setText(" ");
/*      */     
/*  978 */     this.menuBar.setFont(this.menuBar.getFont());
/*      */     
/*  980 */     this.fileMenu.setText("File");
/*  981 */     this.fileMenu.setFont(this.fileMenu.getFont());
/*      */     
/*  983 */     this.openDatasetMenuItem.setAccelerator(KeyStroke.getKeyStroke(79, 2));
/*  984 */     this.openDatasetMenuItem.setFont(this.openDatasetMenuItem.getFont());
/*  985 */     this.openDatasetMenuItem.setText("Open Dataset...");
/*  986 */     this.openDatasetMenuItem.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent evt) {
/*  988 */             DefaultNewhallFrame.this.openDatasetMenuItemActionPerformed(evt);
/*      */           }
/*      */         });
/*  991 */     this.fileMenu.add(this.openDatasetMenuItem);
/*      */     
/*  993 */     this.exitMenuItem.setFont(this.exitMenuItem.getFont());
/*  994 */     this.exitMenuItem.setText("Exit");
/*  995 */     this.exitMenuItem.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent evt) {
/*  997 */             DefaultNewhallFrame.this.exitMenuItemActionPerformed(evt);
/*      */           }
/*      */         });
/* 1000 */     this.fileMenu.add(this.exitMenuItem);
/*      */     
/* 1002 */     this.menuBar.add(this.fileMenu);
/*      */     
/* 1004 */     this.optionsMenu.setText("Options");
/* 1005 */     this.optionsMenu.setFont(this.optionsMenu.getFont());
/*      */     
/* 1007 */     this.toggleUnitsMenuItem.setFont(this.toggleUnitsMenuItem.getFont());
/* 1008 */     this.toggleUnitsMenuItem.setText("Toggle English/Metric Units");
/* 1009 */     this.toggleUnitsMenuItem.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent evt) {
/* 1011 */             DefaultNewhallFrame.this.toggleUnitsMenuItemActionPerformed(evt);
/*      */           }
/*      */         });
/* 1014 */     this.optionsMenu.add(this.toggleUnitsMenuItem);
/*      */     
/* 1016 */     this.menuBar.add(this.optionsMenu);
/*      */     
/* 1018 */     this.helpMenu.setText("Help");
/* 1019 */     this.helpMenu.setFont(this.helpMenu.getFont());
/*      */     
/* 1021 */     this.aboutMenuItem.setFont(this.aboutMenuItem.getFont());
/* 1022 */     this.aboutMenuItem.setText("About");
/* 1023 */     this.aboutMenuItem.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent evt) {
/* 1025 */             DefaultNewhallFrame.this.aboutMenuItemActionPerformed(evt);
/*      */           }
/*      */         });
/* 1028 */     this.helpMenu.add(this.aboutMenuItem);
/*      */     
/* 1030 */     this.menuBar.add(this.helpMenu);
/*      */     
/* 1032 */     setJMenuBar(this.menuBar);
/*      */     
/* 1034 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 1035 */     getContentPane().setLayout(layout);
/* 1036 */     layout.setHorizontalGroup(layout
/* 1037 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 1038 */         .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/* 1039 */           .addContainerGap()
/* 1040 */           .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/* 1041 */             .addComponent(this.tabPane, GroupLayout.Alignment.LEADING, -1, 963, 32767)
/* 1042 */             .addGroup(layout.createSequentialGroup()
/* 1043 */               .addComponent(this.jLabel6)
/* 1044 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 1045 */               .addComponent(this.whcSpinner, -2, 57, -2)
/* 1046 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 1047 */               .addComponent(this.whcUnitsText, -2, 31, -2)
/* 1048 */               .addGap(58, 58, 58)
/* 1049 */               .addComponent(this.activeUnitSystemLabel)
/* 1050 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 1051 */               .addComponent(this.activeUnitSystemText)
/* 1052 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 258, 32767)
/* 1053 */               .addComponent(this.exportXmlButton)
/* 1054 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 1055 */               .addComponent(this.exportCsvButton)))
/* 1056 */           .addContainerGap()));
/*      */     
/* 1058 */     layout.setVerticalGroup(layout
/* 1059 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 1060 */         .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/* 1061 */           .addContainerGap()
/* 1062 */           .addComponent(this.tabPane, -1, 460, 32767)
/* 1063 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 1064 */           .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 1065 */             .addComponent(this.exportCsvButton, GroupLayout.Alignment.TRAILING, -1, -1, 32767)
/* 1066 */             .addComponent(this.exportXmlButton, GroupLayout.Alignment.TRAILING, -1, -1, 32767)
/* 1067 */             .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 1068 */               .addComponent(this.jLabel6)
/* 1069 */               .addComponent(this.whcSpinner, -2, -1, -2)
/* 1070 */               .addComponent(this.whcUnitsText)
/* 1071 */               .addComponent(this.activeUnitSystemLabel)
/* 1072 */               .addComponent(this.activeUnitSystemText)))
/* 1073 */           .addContainerGap()));
/*      */ 
/*      */     
/* 1076 */     pack();
/*      */   }
/*      */   
/*      */   private void exitMenuItemActionPerformed(ActionEvent evt) {
/* 1080 */     System.exit(0);
/*      */   }
/*      */   
/*      */   private void openDatasetMenuItemActionPerformed(ActionEvent evt) {
/* 1084 */     JFileChooser jfc = new JFileChooser(".");
/* 1085 */     int returnCondition = jfc.showOpenDialog(this);
/* 1086 */     if (returnCondition == 0) {
/* 1087 */       System.out.println("Attempting XML parsing.");
/* 1088 */       File selectedFile = jfc.getSelectedFile();
/* 1089 */       NewhallDataset newDataset = null;
/* 1090 */       boolean goodLoad = false;
/*      */       try {
/* 1092 */         XMLFileParser xfp = new XMLFileParser(selectedFile);
/* 1093 */         newDataset = xfp.getDataset();
/* 1094 */         goodLoad = true;
/* 1095 */       } catch (Exception e) {
/* 1096 */         System.out.println("Attempting CSV parsing.");
/*      */         try {
/* 1098 */           CSVFileParser cfp = new CSVFileParser(selectedFile);
/* 1099 */           newDataset = cfp.getDatset();
/* 1100 */           goodLoad = true;
/* 1101 */         } catch (Exception ee) {
/* 1102 */           System.out.println("Unacceptable file detected.");
/* 1103 */           JOptionPane.showMessageDialog(this, "Selected file is not formatted as a Newhall CSV or XML document.");
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1108 */       if (goodLoad) {
/* 1109 */         System.out.println("File acceptable, loading.");
/* 1110 */         this.nd = newDataset;
/* 1111 */         this.loaded = false;
/* 1112 */         loadDataset();
/* 1113 */         this.loaded = true;
/*      */       } else {
/* 1115 */         System.out.println("File unacceptable, unloading all active data.");
/* 1116 */         this.loaded = false;
/* 1117 */         unloadDataset();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void toggleUnitsMenuItemActionPerformed(ActionEvent evt) {
/* 1124 */     double originalWhcValue = 0.0D;
/* 1125 */     double originalSoilAirValue = 0.0D;
/*      */     
/* 1127 */     if (this.whcSpinner.getValue() instanceof Double) {
/* 1128 */       originalWhcValue = ((Double)this.whcSpinner.getValue()).doubleValue();
/*      */     } else {
/* 1130 */       originalWhcValue = ((Integer)this.whcSpinner.getValue()).intValue();
/*      */     } 
/*      */     
/* 1133 */     if (this.soilAirOffset.getValue() instanceof Double) {
/* 1134 */       originalSoilAirValue = ((Double)this.soilAirOffset.getValue()).doubleValue();
/*      */     } else {
/* 1136 */       originalSoilAirValue = ((Integer)this.soilAirOffset.getValue()).intValue();
/*      */     } 
/*      */     
/* 1139 */     this.inMetric = !this.inMetric;
/*      */     
/* 1141 */     if (this.inMetric) {
/* 1142 */       double whcInMm = originalWhcValue * 25.4D;
/* 1143 */       double savInC = originalSoilAirValue * 0.5555555555555556D;
/* 1144 */       this.whcSpinner.setValue(Double.valueOf(whcInMm));
/* 1145 */       this.soilAirOffset.setValue(Double.valueOf(savInC));
/* 1146 */       this.whcUnitsText.setText("mm");
/* 1147 */       this.soilAirOffsetUnits.setText("°C");
/* 1148 */       this.activeUnitSystemText.setText("Metric");
/*      */     } else {
/* 1150 */       double whcInInches = originalWhcValue * 0.0393700787D;
/* 1151 */       double savInF = originalSoilAirValue * 1.8D;
/* 1152 */       this.whcSpinner.setValue(Double.valueOf(whcInInches));
/* 1153 */       this.soilAirOffset.setValue(Double.valueOf(savInF));
/* 1154 */       this.whcUnitsText.setText("in");
/* 1155 */       this.soilAirOffsetUnits.setText("°F");
/* 1156 */       this.activeUnitSystemText.setText("English");
/*      */     } 
/*      */     
/* 1159 */     if (this.nd != null) {
/* 1160 */       loadDataset();
/*      */     }
/*      */   }
/*      */   
/*      */   private void whcSpinnerStateChanged(ChangeEvent evt) {
/* 1165 */     if (this.nd != null && this.loaded) {
/* 1166 */       runModel();
/*      */     }
/*      */   }
/*      */   
/*      */   private void aboutMenuItemActionPerformed(ActionEvent evt) {
/* 1171 */     AboutFrame af = new AboutFrame();
/* 1172 */     af.setLocation(150, 150);
/* 1173 */     af.setVisible(true);
/*      */   }
/*      */   
/*      */   private void exportCsvButtonActionPerformed(ActionEvent evt) {
/* 1177 */     JFileChooser jfc = new JFileChooser(".");
/* 1178 */     int saveDialogResult = jfc.showSaveDialog(this);
/* 1179 */     if (saveDialogResult == 0) {
/* 1180 */       if (jfc.getSelectedFile() != null && jfc.getSelectedFile().exists()) {
/* 1181 */         int result = JOptionPane.showConfirmDialog(null, "The file " + jfc.getSelectedFile().getName() + " already exists, overwrite it?", "Confirm Overwrite", 2, 3);
/*      */         
/* 1183 */         if (result == 2) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */       
/* 1188 */       CSVResultsExporter cve = new CSVResultsExporter(this.nr, jfc.getSelectedFile());
/* 1189 */       cve.save();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void exportXmlButtonActionPerformed(ActionEvent evt) {
/* 1194 */     JFileChooser jfc = new JFileChooser(".");
/* 1195 */     int saveDialogResult = jfc.showSaveDialog(this);
/* 1196 */     if (saveDialogResult == 0) {
/* 1197 */       if (jfc.getSelectedFile() != null && jfc.getSelectedFile().exists()) {
/* 1198 */         int result = JOptionPane.showConfirmDialog(null, "The file " + jfc.getSelectedFile().getName() + " already exists, overwrite it?", "Confirm Overwrite", 2, 3);
/*      */         
/* 1200 */         if (result == 2) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */       
/*      */       try {
/* 1206 */         XMLResultsExporter xre = new XMLResultsExporter(jfc.getSelectedFile());
/* 1207 */         xre.export(this.nr, this.nd);
/* 1208 */         System.out.println("Exported XML:\n------------------------");
/* 1209 */         System.out.print(XMLStringResultsExporter.export(this.nr, this.nd));
/* 1210 */         System.out.println("------------------------");
/* 1211 */       } catch (IOException e) {
/* 1212 */         System.out.println("XML Export failed: " + e.getMessage());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void soilAirOffsetStateChanged(ChangeEvent evt) {
/* 1219 */     double newOffset = 0.0D;
/*      */     
/* 1221 */     if (this.soilAirOffset.getValue() instanceof Integer) {
/* 1222 */       newOffset = ((Integer)this.soilAirOffset.getValue()).intValue();
/*      */     } else {
/* 1224 */       newOffset = ((Double)this.soilAirOffset.getValue()).doubleValue();
/*      */     } 
/*      */     
/* 1227 */     if (this.inMetric && !this.nd.isMetric()) {
/*      */       
/* 1229 */       this.nd.getMetadata().setSoilAirOffset(newOffset * 1.8D);
/* 1230 */     } else if (!this.inMetric && this.nd.isMetric()) {
/*      */       
/* 1232 */       this.nd.getMetadata().setSoilAirOffset(newOffset * 0.5555555555555556D);
/*      */     } else {
/* 1234 */       this.nd.getMetadata().setSoilAirOffset(newOffset);
/*      */     } 
/*      */     
/* 1237 */     if (this.nd != null && this.loaded) {
/* 1238 */       runModel();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void amplitudeSpinnerStateChanged(ChangeEvent evt) {
/* 1245 */     double newAmplitude = 0.0D;
/*      */     
/* 1247 */     if (this.amplitudeSpinner.getValue() instanceof Integer) {
/* 1248 */       newAmplitude = ((Integer)this.amplitudeSpinner.getValue()).intValue();
/*      */     } else {
/* 1250 */       newAmplitude = ((Double)this.amplitudeSpinner.getValue()).doubleValue();
/*      */     } 
/*      */     
/* 1253 */     if (this.nd != null && this.loaded) {
/* 1254 */       this.nd.getMetadata().setAmplitude(newAmplitude);
/* 1255 */       runModel();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadDataset() {
/* 1263 */     List<Double> properTemp = new ArrayList<Double>(12);
/* 1264 */     List<Double> properPrecip = new ArrayList<Double>(12);
/* 1265 */     double properElevation = 0.0D;
/*      */     
/* 1267 */     if (this.inMetric && !this.nd.isMetric()) {
/*      */       
/* 1269 */       for (int j = 0; j < 12; j++) {
/* 1270 */         double tempInC = (((Double)this.nd.getTemperature().get(j)).doubleValue() - 32.0D) * 0.5555555555555556D;
/* 1271 */         properTemp.add(Double.valueOf(tempInC));
/* 1272 */         double precipInMm = ((Double)this.nd.getPrecipitation().get(j)).doubleValue() * 25.4D;
/* 1273 */         properPrecip.add(Double.valueOf(precipInMm));
/*      */       } 
/*      */       
/* 1276 */       double offsetInC = this.nd.getMetadata().getSoilAirOffset() * 0.5555555555555556D;
/* 1277 */       this.soilAirOffset.setValue(Double.valueOf(offsetInC));
/* 1278 */       properElevation = this.nd.getElevation() * 0.3048D;
/*      */     }
/* 1280 */     else if (!this.inMetric && this.nd.isMetric()) {
/*      */       
/* 1282 */       for (int j = 0; j < 12; j++) {
/* 1283 */         double tempInF = ((Double)this.nd.getTemperature().get(j)).doubleValue() * 1.8D + 32.0D;
/* 1284 */         properTemp.add(Double.valueOf(tempInF));
/* 1285 */         double precipInInches = ((Double)this.nd.getPrecipitation().get(j)).doubleValue() / 25.4D;
/* 1286 */         properPrecip.add(Double.valueOf(precipInInches));
/*      */       } 
/*      */       
/* 1289 */       double whcInInches = this.nd.getWaterholdingCapacity() * 0.0393700787D;
/* 1290 */       this.whcSpinner.setValue(Double.valueOf(whcInInches));
/* 1291 */       double offsetInF = this.nd.getMetadata().getSoilAirOffset() * 1.8D;
/* 1292 */       this.soilAirOffset.setValue(Double.valueOf(offsetInF));
/* 1293 */       properElevation = this.nd.getElevation() * 3.2808399D;
/*      */     } else {
/*      */       
/* 1296 */       properTemp = this.nd.getTemperature();
/* 1297 */       properPrecip = this.nd.getPrecipitation();
/* 1298 */       properElevation = this.nd.getElevation();
/* 1299 */       this.whcSpinner.setValue(Double.valueOf(this.nd.getWaterholdingCapacity()));
/* 1300 */       this.soilAirOffset.setValue(Double.valueOf(this.nd.getMetadata().getSoilAirOffset()));
/*      */     } 
/*      */     
/* 1303 */     TableModel tempTableModel = this.tempTable.getModel();
/* 1304 */     TableModel rainfallTableModel = this.rainfallTable.getModel();
/* 1305 */     if (this.inMetric) {
/* 1306 */       tempTableModel.setValueAt("Air Temp (°C)", 0, 0);
/* 1307 */       rainfallTableModel.setValueAt("Rainfall (mm)", 0, 0);
/* 1308 */       this.elevationText.setText(roundForDisplay(Double.valueOf(properElevation)) + " meters");
/* 1309 */       this.elevation.setText(roundForDisplay(Double.valueOf(properElevation)) + " meters");
/* 1310 */       this.soilAirOffsetUnits.setText("°C");
/* 1311 */       this.activeUnitSystemText.setText("Metric");
/*      */     } else {
/* 1313 */       tempTableModel.setValueAt("Air Temp (°F)", 0, 0);
/* 1314 */       rainfallTableModel.setValueAt("Rainfall (in)", 0, 0);
/* 1315 */       this.elevationText.setText(roundForDisplay(Double.valueOf(properElevation)) + " feet");
/* 1316 */       this.elevation.setText(roundForDisplay(Double.valueOf(properElevation)) + " feet");
/* 1317 */       this.soilAirOffsetUnits.setText("°F");
/* 1318 */       this.activeUnitSystemText.setText("English");
/*      */     } 
/*      */     
/* 1321 */     this.tempTable.getColumnModel().getColumn(0).setPreferredWidth(150);
/* 1322 */     this.rainfallTable.getColumnModel().getColumn(0).setPreferredWidth(150);
/* 1323 */     for (int i = 0; i < 12; i++) {
/* 1324 */       rainfallTableModel.setValueAt(Double.valueOf(roundForDisplay(properPrecip.get(i))), 0, i + 1);
/* 1325 */       tempTableModel.setValueAt(Double.valueOf(roundForDisplay(properTemp.get(i))), 0, i + 1);
/*      */     } 
/*      */     
/* 1328 */     this.srcUnitSystem.setText(this.nd.getMetadata().getUnitSystem());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1336 */     this.amplitudeSpinner.setValue(Double.valueOf(this.nd.getMetadata().getAmplitude()));
/*      */     
/* 1338 */     this.stationText.setText(this.nd.getName());
/* 1339 */     this.latitudeText.setText(roundDegreesForDisplay(Double.valueOf(this.nd.getLatitude())) + " " + '°' + this.nd.getNsHemisphere());
/* 1340 */     this.longitudeText.setText(roundDegreesForDisplay(Double.valueOf(this.nd.getLongitude())) + " " + '°' + this.nd.getEwHemisphere());
/* 1341 */     this.countryText.setText(this.nd.getCountry());
/* 1342 */     this.startingYearText.setText(this.nd.getStartYear() + "");
/* 1343 */     this.endingYearText.setText(this.nd.getEndYear() + "");
/*      */     
/* 1345 */     this.whcSpinner.setEnabled(true);
/* 1346 */     this.soilAirOffset.setEnabled(true);
/* 1347 */     this.amplitudeSpinner.setEnabled(true);
/* 1348 */     this.exportCsvButton.setEnabled(true);
/* 1349 */     this.exportXmlButton.setEnabled(true);
/*      */     
/* 1351 */     this.stationName.setText(this.nd.getMetadata().getStationName());
/* 1352 */     this.stationId.setText(this.nd.getMetadata().getStationId());
/* 1353 */     this.stationStateProv.setText(this.nd.getMetadata().getStationStateProvidence());
/* 1354 */     this.stationCountry.setText(this.nd.getMetadata().getStationCountry());
/* 1355 */     this.mlraName.setText(this.nd.getMetadata().getMlraName());
/* 1356 */     this.mlraId.setText(Integer.toString(this.nd.getMetadata().getMlraId()));
/*      */     
/* 1358 */     this.firstName.setText(this.nd.getMetadata().getContribFirstName());
/* 1359 */     this.lastName.setText(this.nd.getMetadata().getContribLastName());
/* 1360 */     this.title.setText(this.nd.getMetadata().getContribTitle());
/* 1361 */     this.orginization.setText(this.nd.getMetadata().getContribOrg());
/* 1362 */     this.address.setText(this.nd.getMetadata().getContribAddress());
/* 1363 */     this.city.setText(this.nd.getMetadata().getContribCity());
/* 1364 */     this.stateProv.setText(this.nd.getMetadata().getContribStateProvidence());
/* 1365 */     this.postal.setText(this.nd.getMetadata().getContribPostal());
/* 1366 */     this.country.setText(this.nd.getMetadata().getContribCountry());
/* 1367 */     this.email.setText(this.nd.getMetadata().getContribEmail());
/* 1368 */     this.phone.setText(this.nd.getMetadata().getContribPhone());
/*      */     
/* 1370 */     String totalNotes = "";
/* 1371 */     for (String note : this.nd.getMetadata().getNotes()) {
/* 1372 */       totalNotes = totalNotes + note + "\n\n";
/*      */     }
/* 1374 */     this.notes.setText(totalNotes);
/*      */ 
/*      */     
/* 1377 */     runModel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void runModel() {
/* 1384 */     double inputWhc = 0.0D;
/* 1385 */     double inputOffset = this.nd.getMetadata().getSoilAirOffset();
/*      */     
/* 1387 */     if (this.whcSpinner.getValue() instanceof Integer) {
/* 1388 */       inputWhc = ((Integer)this.whcSpinner.getValue()).intValue();
/*      */     } else {
/* 1390 */       inputWhc = ((Double)this.whcSpinner.getValue()).doubleValue();
/*      */     } 
/* 1392 */     if (!this.inMetric)
/*      */     {
/* 1394 */       inputWhc *= 25.4D;
/*      */     }
/*      */     
/* 1397 */     if (!this.nd.isMetric())
/*      */     {
/* 1399 */       inputOffset *= 0.5555555555555556D;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1405 */       this.nr = null;
/* 1406 */       System.out.println("Calling model: " + this.nd.getName() + ", " + inputWhc + ", " + inputOffset + ", " + this.nd
/* 1407 */           .getMetadata().getAmplitude());
/* 1408 */       this.nr = BASICSimulationModel.runSimulation(this.nd, inputWhc, inputOffset, this.nd.getMetadata().getAmplitude());
/* 1409 */     } catch (Exception e) {
/* 1410 */       e.printStackTrace();
/* 1411 */       unloadDataset();
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1417 */     if (this.nr != null) {
/*      */       String properAnnualRainfall;
/* 1419 */       TableModel mpeTableModel = this.mpeTable.getModel();
/* 1420 */       this.mpeTable.getColumnModel().getColumn(0).setPreferredWidth(260);
/*      */ 
/*      */ 
/*      */       
/* 1424 */       if (!this.inMetric) {
/*      */         
/* 1426 */         properAnnualRainfall = roundForDisplay(Double.valueOf(this.nr.getAnnualRainfall() * 0.0393700787D)) + " in";
/* 1427 */         this.whcUnitsText.setText("in");
/* 1428 */         mpeTableModel.setValueAt("Evapotranspiration (in)", 0, 0);
/* 1429 */         for (int i = 0; i < 12; i++) {
/* 1430 */           double mpeValueForDisplay = roundForDisplay(Double.valueOf(((Double)this.nr.getMeanPotentialEvapotranspiration().get(i)).doubleValue() * 0.0393700787D));
/* 1431 */           mpeTableModel.setValueAt(Double.valueOf(mpeValueForDisplay), 0, i + 1);
/*      */         } 
/*      */       } else {
/* 1434 */         properAnnualRainfall = roundForDisplay(Double.valueOf(this.nr.getAnnualRainfall())) + " mm";
/* 1435 */         this.whcUnitsText.setText("mm");
/* 1436 */         mpeTableModel.setValueAt("Evapotranspiration (mm)", 0, 0);
/* 1437 */         for (int i = 0; i < 12; i++) {
/* 1438 */           double mpeValueForDisplay = roundForDisplay(this.nr.getMeanPotentialEvapotranspiration().get(i));
/* 1439 */           mpeTableModel.setValueAt(Double.valueOf(mpeValueForDisplay), 0, i + 1);
/*      */         } 
/*      */       } 
/*      */       
/* 1443 */       this.annualRainfallText.setText(properAnnualRainfall);
/* 1444 */       this.temperatureRegimeText.setText(this.nr.getTemperatureRegime());
/* 1445 */       this.moistureRegimeText.setText(this.nr.getMoistureRegime());
/* 1446 */       this.subdivisionsText.setText(this.nr.getRegimeSubdivision1() + " " + this.nr.getRegimeSubdivision2());
/* 1447 */       this.moistureCalendarText.setText(this.nr.getFormattedMoistureCalendar());
/* 1448 */       this.temperatureCalendarText.setText(this.nr.getFormattedTemperatureCalendar());
/* 1449 */       this.statisticsText.setText(this.nr.getFormattedStatistics());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void unloadDataset() {
/* 1456 */     this.stationText.setText(" << No Dataset Loaded >>");
/* 1457 */     this.elevationText.setText("");
/* 1458 */     this.latitudeText.setText("");
/* 1459 */     this.longitudeText.setText("");
/* 1460 */     this.countryText.setText("");
/* 1461 */     this.startingYearText.setText("");
/* 1462 */     this.endingYearText.setText("");
/*      */     
/* 1464 */     TableModel mpeTableModel = this.mpeTable.getModel();
/* 1465 */     TableModel rainfallTableModel = this.rainfallTable.getModel();
/* 1466 */     TableModel tempTableModel = this.tempTable.getModel();
/*      */     int i;
/* 1468 */     for (i = 0; i <= 12; i++) {
/* 1469 */       rainfallTableModel.setValueAt("", 0, i);
/* 1470 */       tempTableModel.setValueAt("", 0, i);
/*      */     } 
/*      */     
/* 1473 */     for (i = 0; i <= 12; i++) {
/* 1474 */       mpeTableModel.setValueAt("", 0, i);
/*      */     }
/*      */     
/* 1477 */     this.annualRainfallText.setText("");
/* 1478 */     this.temperatureRegimeText.setText("");
/* 1479 */     this.moistureRegimeText.setText("");
/* 1480 */     this.subdivisionsText.setText("");
/*      */     
/* 1482 */     this.temperatureCalendarText.setText("");
/* 1483 */     this.moistureCalendarText.setText("");
/* 1484 */     this.statisticsText.setText("");
/*      */     
/* 1486 */     this.whcSpinner.setValue(Double.valueOf(200.0D));
/* 1487 */     this.whcSpinner.setEnabled(false);
/* 1488 */     this.soilAirOffset.setValue(Double.valueOf(1.2D));
/* 1489 */     this.soilAirOffset.setEnabled(false);
/* 1490 */     this.amplitudeSpinner.setValue(Double.valueOf(0.66D));
/* 1491 */     this.amplitudeSpinner.setEnabled(false);
/* 1492 */     this.exportCsvButton.setEnabled(false);
/* 1493 */     this.exportXmlButton.setEnabled(false);
/*      */     
/* 1495 */     this.stationName.setText("");
/* 1496 */     this.stationId.setText("");
/* 1497 */     this.elevation.setText("");
/* 1498 */     this.stationStateProv.setText("");
/* 1499 */     this.stationCountry.setText("");
/* 1500 */     this.mlraName.setText("");
/* 1501 */     this.mlraId.setText("");
/*      */     
/* 1503 */     this.firstName.setText("");
/* 1504 */     this.lastName.setText("");
/* 1505 */     this.title.setText("");
/* 1506 */     this.orginization.setText("");
/* 1507 */     this.address.setText("");
/* 1508 */     this.city.setText("");
/* 1509 */     this.stateProv.setText("");
/* 1510 */     this.postal.setText("");
/* 1511 */     this.country.setText("");
/* 1512 */     this.email.setText("");
/* 1513 */     this.phone.setText("");
/*      */     
/* 1515 */     this.notes.setText("");
/*      */     
/* 1517 */     this.activeUnitSystemText.setText("");
/* 1518 */     this.inMetric = true;
/*      */   }
/*      */   
/*      */   public double roundForDisplay(Double value) {
/* 1522 */     return Double.valueOf((new DecimalFormat("##.##")).format(value)).doubleValue();
/*      */   }
/*      */   
/*      */   public double roundDegreesForDisplay(Double degrees) {
/* 1526 */     return Double.valueOf((new DecimalFormat("##.####")).format(degrees)).doubleValue();
/*      */   } }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/psu/newhall/ui/DefaultNewhallFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
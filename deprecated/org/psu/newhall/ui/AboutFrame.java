/*     */ package org.psu.newhall.ui;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JToggleButton;
/*     */ import javax.swing.LayoutStyle;
/*     */ import org.psu.newhall.Newhall;
/*     */ 
/*     */ public class AboutFrame
/*     */   extends JFrame {
/*     */   private JTextArea aboutText;
/*     */   private JToggleButton closeButton;
/*     */   private JLabel jLabel1;
/*     */   
/*     */   public AboutFrame() {
/*  22 */     initComponents();
/*     */   }
/*     */ 
/*     */   
/*     */   private JLabel jLabel3;
/*     */   
/*     */   private JScrollPane jScrollPane1;
/*     */   
/*     */   private JLabel versionText;
/*     */ 
/*     */   
/*     */   private void initComponents() {
/*  34 */     this.jLabel1 = new JLabel();
/*  35 */     this.jScrollPane1 = new JScrollPane();
/*  36 */     this.aboutText = new JTextArea();
/*  37 */     this.jLabel3 = new JLabel();
/*  38 */     this.versionText = new JLabel();
/*  39 */     this.closeButton = new JToggleButton();
/*     */     
/*  41 */     setDefaultCloseOperation(2);
/*  42 */     setTitle("About Newhall");
/*  43 */     setResizable(false);
/*     */     
/*  45 */     this.jLabel1.setFont(this.jLabel1.getFont().deriveFont(this.jLabel1.getFont().getSize() + 30.0F));
/*  46 */     this.jLabel1.setText("newhall");
/*     */     
/*  48 */     this.aboutText.setColumns(20);
/*  49 */     this.aboutText.setEditable(false);
/*  50 */     this.aboutText.setFont(new Font("Monospaced", 0, 11));
/*  51 */     this.aboutText.setRows(5);
/*  52 */     this.aboutText.setText("Newhall " + Newhall.NSM_VERSION + ", Copyright (C) 2010-2011 \n" + "" + "United States Department of Agriculture - Natural Resources Conservation Service, \n" + "Penn State University Center for Environmental Informatics\n" + "All rights reserved.\n" + "\n(The following is known as the Three-Clause or \"New\" BSD license.)\n\nRedistribution and use in source and binary forms, with or without\nmodification, are permitted provided that the following conditions are met:\n\n    * Redistributions of source code must retain the above copyright\n      notice, this list of conditions and the following disclaimer.\n\n    * Redistributions in binary form must reproduce the above copyright\n      notice, this list of conditions and the following disclaimer in the\n      documentation and/or other materials provided with the distribution.\n\n    * Neither the name of the copyright holder nor the\n      names of its contributors may be used to endorse or promote products\n      derived from this software without specific prior written permission.\n\nTHIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\" AND\nANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED\nWARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE\nDISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY\nDIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES\n(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;\nLOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND\nON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT\n(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS\nSOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n\n===\n\nJDOM 1.1, Copyright (C) 2000-2004 Jason Hunter & Brett McLaughlin.\nAll rights reserved.\n\nRedistribution and use in source and binary forms, with or without\nmodification, are permitted provided that the following conditions\nare met:\n\n1. Redistributions of source code must retain the above copyright\n   notice, this list of conditions, and the following disclaimer.\n\n2. Redistributions in binary form must reproduce the above copyright\n   notice, this list of conditions, and the disclaimer that follows\n   these conditions in the documentation and/or other materials\n   provided with the distribution.\n\n3. The name \"JDOM\" must not be used to endorse or promote products\n   derived from this software without prior written permission.  For\n   written permission, please contact <request_AT_jdom_DOT_org>.\n\n4. Products derived from this software may not be called \"JDOM\", nor\n   may \"JDOM\" appear in their name, without prior written permission\n   from the JDOM Project Management <request_AT_jdom_DOT_org>.\n\nIn addition, we request (but do not require) that you include in the\nend-user documentation provided with the redistribution and/or in the\nsoftware itself an acknowledgement equivalent to the following:\n\n    \"This product includes software developed by the\n     JDOM Project (http://www.jdom.org/).\"\n\nAlternatively, the acknowledgment may be graphical using the logos\navailable at http://www.jdom.org/images/logos.\n\nTHIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED\nWARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES\nOF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE\nDISCLAIMED.  IN NO EVENT SHALL THE JDOM AUTHORS OR THE PROJECT\nCONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,\nSPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT\nLIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF\nUSE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND\nON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,\nOR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT\nOF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF\nSUCH DAMAGE.\n\nThis software consists of voluntary contributions made by many\nindividuals on behalf of the JDOM Project and was originally\ncreated by Jason Hunter <jhunter_AT_jdom_DOT_org> and\nBrett McLaughlin <brett_AT_jdom_DOT_org>.  For more information\non the JDOM Project, please see <http://www.jdom.org/>.");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     this.aboutText.setCaretPosition(1);
/*  58 */     this.jScrollPane1.setViewportView(this.aboutText);
/*     */     
/*  60 */     this.jLabel3.setHorizontalAlignment(4);
/*  61 */     this.jLabel3.setText("<html>A Java implementation of the Newhall model for soil regime simulation.<br>\nBased on Armand Wambeke's BASIC version of Franklin Newhall's model.<br>\nhttps://github.com/drww/newhall</html>");
/*     */     
/*  63 */     this.versionText.setText(Newhall.NSM_VERSION);
/*     */     
/*  65 */     this.closeButton.setText("Close");
/*  66 */     this.closeButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*  68 */             AboutFrame.this.closeButtonActionPerformed(evt);
/*     */           }
/*     */         });
/*     */     
/*  72 */     GroupLayout layout = new GroupLayout(getContentPane());
/*  73 */     getContentPane().setLayout(layout);
/*  74 */     layout.setHorizontalGroup(layout
/*  75 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  76 */         .addGroup(layout.createSequentialGroup()
/*  77 */           .addContainerGap()
/*  78 */           .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  79 */             .addComponent(this.jScrollPane1, -1, 757, 32767)
/*  80 */             .addGroup(layout.createSequentialGroup()
/*  81 */               .addComponent(this.jLabel1)
/*  82 */               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  83 */               .addComponent(this.versionText, -2, 74, 32767)
/*  84 */               .addGap(7, 7, 7)
/*  85 */               .addComponent(this.jLabel3, -2, -1, -2))
/*  86 */             .addComponent(this.closeButton, GroupLayout.Alignment.TRAILING))
/*  87 */           .addContainerGap()));
/*     */     
/*  89 */     layout.setVerticalGroup(layout
/*  90 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  91 */         .addGroup(layout.createSequentialGroup()
/*  92 */           .addContainerGap()
/*  93 */           .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  94 */             .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  95 */               .addComponent(this.jLabel1)
/*  96 */               .addComponent(this.versionText))
/*  97 */             .addComponent(this.jLabel3, -2, -1, -2))
/*  98 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  99 */           .addComponent(this.jScrollPane1, -1, 372, 32767)
/* 100 */           .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 101 */           .addComponent(this.closeButton)
/* 102 */           .addContainerGap()));
/*     */ 
/*     */     
/* 105 */     pack();
/*     */   }
/*     */   
/*     */   private void closeButtonActionPerformed(ActionEvent evt) {
/* 109 */     setVisible(false);
/*     */   }
/*     */ }


/* Location:              /home/andrew/workspace/jNSMR/inst/jNSM/jNSM_v1.6.1_public_bundle/libs/newhall-1.6.1.jar!/org/psu/newhall/ui/AboutFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
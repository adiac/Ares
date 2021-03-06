/*
 Copyright (c) 2010 [Joerg Ruedenauer]
 
 This file is part of Ares.

 Ares is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 Ares is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Ares; if not, write to the Free Software
 Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package ares.controller.gui;

import ares.controller.gui.lf.BGDialog;
import ares.controller.gui.lf.LookAndFeels;
import ares.controller.gui.util.ExampleFileFilter;
import ares.controllers.messages.Message;
import ares.controller.util.Directories;
import ares.controller.util.Localization;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import javax.swing.JRadioButton;

import com.l2fprod.common.swing.JDirectoryChooser;
import com.l2fprod.common.swing.JFontChooser;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;

public final class OptionsDialog extends BGDialog {

  private JPanel jContentPane = null;

  private JPanel jPanel = null;

  private JButton jButton = null;

  private JButton jButton1 = null;

  private JTabbedPane jTabbedPane = null;

  private JPanel jPanel1 = null;

  private JCheckBox fgBox = null;

  private JLabel jLabel = null;

  private JComboBox lfBox = null;

  private JLabel jLabel1 = null;

  private JLabel jLabel2 = null;

  private JTextField skinField = null;

  private JButton skinButton = null;

  private JPanel programPanel = null;

  private JLabel jLabel3 = null;

  private JRadioButton dataHomeDirButton = null;

  private JRadioButton dataProgramDirButton = null;

  private JRadioButton dataCustomDirButton = null;

  private JTextField dataCustomDirBox = null;

  private JButton selDataDirButton = null;

  private JLabel jLabel4 = null;

  private JSpinner udpSpinner = null;

  private JLabel jLabel5 = null;

  private JComboBox messageLevelBox = null;
  
  private JCheckBox updateCheckBox = null;
  
  private JCheckBox keysCheckBox = null;
  
  private JCheckBox musicOnAllSpeakersBox = null;
  
  private JRadioButton noFadeButton = null;
  
  private JRadioButton fadeButton = null;
  
  private JRadioButton crossFadeButton = null;
  
  private JSpinner fadeTimeSpinner = null;
  
  private JRadioButton autoDetectButton = null;
  private JRadioButton manualSelectButton = null;
  private JTextField addressField = null;
  private JSpinner tcpSpinner = null;
  private JTextField serverNameField = null;

  /**
   * This method initializes
   * 
   */
  public OptionsDialog(JFrame owner) {
    super(owner);
    initialize();
  }
  
  public void setMusicOptions(boolean onAllSpeakers, int musicFadingOption, int musicFadingTime) {
	  getMusicOnAllSpeakersBox().setSelected(onAllSpeakers);
	  getNoFadeButton().setSelected(musicFadingOption == 0);
	  getFadeButton().setSelected(musicFadingOption == 1);
	  getCrossFadeButton().setSelected(musicFadingOption == 2);
	  getFadeTimeSpinner().setEnabled(musicFadingOption != 0);
	  ((SpinnerNumberModel)getFadeTimeSpinner().getModel()).setValue(musicFadingTime);
  }
  
  public final String getHelpPage() {
    return Localization.getString("OptionsDialog.SettingsHelp"); //$NON-NLS-1$
  }

  /**
   * This method initializes this
   * 
   */
  private void initialize() {
    this.setContentPane(getJContentPane());
    this.setTitle(Localization.getString("OptionsDialog.Settings")); //$NON-NLS-1$
    updateData();
    onLFSelected();
    this.getRootPane().setDefaultButton(getOKButton());
    setEscapeButton(getCancelButton());
    pack();
  }

  private void onLFSelected() {
    String lf = lfBox.getSelectedItem().toString();
    if (lf.startsWith("Skin")) { //$NON-NLS-1$
      skinField.setEnabled(true);
      skinButton.setEnabled(true);
    }
    else {
      skinField.setEnabled(false);
      skinButton.setEnabled(false);
    }
    if (lf.startsWith("Synthetica") || lf.startsWith("Default")) { //$NON-NLS-1$ //$NON-NLS-2$
      fontButton.setEnabled(true);
      Preferences prefs = Preferences.userNodeForPackage(OptionsDialog.class);
      if (prefs.getBoolean("FontSet", false)) { //$NON-NLS-1$
        String fontName = prefs.get("FontName", "Dialog"); //$NON-NLS-1$ //$NON-NLS-2$
        int fontSize = prefs.getInt("FontSize", 12); //$NON-NLS-1$
        fontField.setText(fontName + " " + fontSize); //$NON-NLS-1$
      }
      else {
        String fontName = SyntheticaLookAndFeel.getFontName();
        int fontSize = SyntheticaLookAndFeel.getFontSize();
        fontField.setText(fontName + " " + fontSize); //$NON-NLS-1$
      }
    }
    else {
      fontButton.setEnabled(false);
      fontField.setText("-"); //$NON-NLS-1$
    }
  }

  /**
   * This method initializes jContentPane
   * 
   * @return javax.swing.JPanel
   */
  private JPanel getJContentPane() {
    if (jContentPane == null) {
      jContentPane = new JPanel();
      jContentPane.setLayout(new BorderLayout());
      jContentPane.add(getJPanel(), java.awt.BorderLayout.SOUTH);
      jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
    }
    return jContentPane;
  }

  /**
   * This method initializes jPanel
   * 
   * @return javax.swing.JPanel
   */
  private JPanel getJPanel() {
    if (jPanel == null) {
      jPanel = new JPanel();
      jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.LINE_AXIS));
      jPanel.add(Box.createHorizontalGlue());
      JButton okButton = getOKButton();
      JButton cancelButton = getCancelButton();
      if (okButton.getPreferredSize().getWidth() < cancelButton.getPreferredSize().getWidth()) {
        okButton.setPreferredSize(cancelButton.getPreferredSize());
      }
      else if (cancelButton.getPreferredSize().getWidth() < okButton.getPreferredSize().getWidth()) {
        cancelButton.setPreferredSize(okButton.getPreferredSize());
      }
      jPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      jPanel.add(getOKButton());
      jPanel.add(Box.createHorizontalStrut(5));
      jPanel.add(getCancelButton());
    }
    return jPanel;
  }

  /**
   * This method initializes jButton
   * 
   * @return javax.swing.JButton
   */
  private JButton getOKButton() {
    if (jButton == null) {
      jButton = new JButton();
      jButton.setText(Localization.getString("OptionsDialog.OK")); //$NON-NLS-1$
      jButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (savePreferences()) {
            dispose();
          }
        }
      });
    }
    return jButton;
  }
  
  private boolean musicOnAllSpeakers = false;
  private int musicFadingOption = 0;
  private int musicFadingTime = 0;
  private boolean closedByOK = false;
  
  public boolean wasClosedByOK() {
	  return closedByOK;
  }
  
  public boolean getMusicOnAllSpeakers() {
	  return musicOnAllSpeakers;
  }
  
  public int getMusicFadingOption() {
	  return musicFadingOption;
  }
  
  public int getMusicFadingTime() {
	  return musicFadingTime;
  }

  protected boolean savePreferences() {
	boolean autoDetectPlayer = getAutoDetectButton().isSelected();
	if (!autoDetectPlayer) {
		String ipAddressString = getIpAddressField().getText();
		if (ipAddressString == null || ipAddressString.length() == 0) {
			JOptionPane.showMessageDialog(this, Localization.getString("OptionsDialog.EnterValidIP"), Localization.getString("OptionsDialog.Ares"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
			return false;			
		}
		try {
			InetAddress.getByName(ipAddressString);
		}
		catch (UnknownHostException ex) {
			JOptionPane.showMessageDialog(this, Localization.getString("OptionsDialog.EnterValidIP"), Localization.getString("OptionsDialog.Ares"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}
		String serverNameString = getServerNameField().getText();
		if (serverNameString == null || serverNameString.length() == 0) {
			JOptionPane.showMessageDialog(this, Localization.getString("OptionsDialog.EnterServerName"), Localization.getString("OptionsDialog.Ares"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
			return false;			
		}
	}
    String lf = lfBox.getSelectedItem().toString();
    Preferences prefs = Preferences.userNodeForPackage(OptionsDialog.class);
    
    if (lf.startsWith("Synthetica") || lf.startsWith("Default")) { //$NON-NLS-1$ //$NON-NLS-2$
      prefs.putBoolean("FontSet", true); //$NON-NLS-1$
      String text = fontField.getText();
      int lastSpace = text.lastIndexOf(" "); //$NON-NLS-1$
      String fontName = text.substring(0, lastSpace);
      int fontSize = 12;
      try {
        fontSize = Integer.parseInt(text.substring(lastSpace + 1));
      }
      catch (NumberFormatException e) {
        fontSize = 12;
      }
      prefs.put("FontName", fontName); //$NON-NLS-1$
      prefs.putInt("FontSize", fontSize); //$NON-NLS-1$
    }
    
    if (lf.startsWith("Skin")) { //$NON-NLS-1$
      String name = skinField.getText();
      if (name == null || name.equals("")) { //$NON-NLS-1$
        javax.swing.JOptionPane.showMessageDialog(this,
            Localization.getString("OptionsDialog.PleaseSelectSkin"), Localization.getString("OptionsDialog.Error"), //$NON-NLS-1$ //$NON-NLS-2$
            javax.swing.JOptionPane.ERROR_MESSAGE);
        return false;
      }
      LookAndFeels.setLookAndFeel(lf, name);
    }
    else {
      LookAndFeels.setLookAndFeel(lf);
    }

    int dataOption = 0;
    if (dataProgramDirButton.isSelected()) {
      dataOption = 1;
    }
    else if (dataCustomDirButton.isSelected()) {
      String dataCustomDir = dataCustomDirBox.getText();
      File test = new File(dataCustomDir);
      if (!test.exists() || !test.isDirectory()) {
        javax.swing.JOptionPane.showMessageDialog(this,
            Localization.getString("OptionsDialog.PleaseSelectDirectory"), Localization.getString("OptionsDialog.Error"), //$NON-NLS-1$ //$NON-NLS-2$
            javax.swing.JOptionPane.ERROR_MESSAGE);
        return false;
      }
      prefs.put("CustomDataDir", dataCustomDir); //$NON-NLS-1$
      dataOption = 2;
    }
    prefs.putInt("CustomDataDirOption", dataOption); //$NON-NLS-1$
    prefs.putBoolean("BringWindowsToTop", fgBox.isSelected()); //$NON-NLS-1$
    int udpPort = ((Number)udpSpinner.getValue()).intValue();
    prefs.putInt("MessageLevel", messageLevelBox.getSelectedIndex()); //$NON-NLS-1$
    prefs.putBoolean("CheckForUpdate", updateCheckBox.isSelected()); //$NON-NLS-1$
    prefs.putBoolean("StartLocalPlayer", startPlayerBox.isSelected()); //$NON-NLS-1$
    prefs.putBoolean("AskForPlayerStart", askBeforePlayerStartBox.isSelected()); //$NON-NLS-1$
    prefs.putBoolean("ShowKeys", keysCheckBox.isSelected()); //$NON-NLS-1$
    
    prefs.putBoolean("AutoDetectPlayer", autoDetectPlayer); //$NON-NLS-1$
    if (getAutoDetectButton().isSelected()) {
        prefs.putInt("UDPPort", udpPort); //$NON-NLS-1$
    }
    else {
    	prefs.put("IPAddress", getIpAddressField().getText()); //$NON-NLS-1$
    	prefs.putInt("TCPPort", ((Number)getTcpSpinner().getValue()).intValue()); //$NON-NLS-1$
    	prefs.put("ServerName", getServerNameField().getText()); //$NON-NLS-1$
    }
    
    musicOnAllSpeakers = getMusicOnAllSpeakersBox().isSelected();
    musicFadingOption = getNoFadeButton().isSelected() ? 0 : (getFadeButton().isSelected() ? 1 : 2);
    musicFadingTime = ((SpinnerNumberModel)getFadeTimeSpinner().getModel()).getNumber().intValue();
    closedByOK = true;

    return true;
  }

  private void updateData() {
    Preferences prefs = Preferences.userNodeForPackage(OptionsDialog.class);
    fgBox.setSelected(prefs.getBoolean("BringWindowsToTop", true)); //$NON-NLS-1$
    udpSpinner.setValue(prefs.getInt("UDPPort", 8009)); //$NON-NLS-1$

    int dataDirOpt = prefs.getInt("CustomDataDirOption", -1); //$NON-NLS-1$
    if (dataDirOpt == -1) dataDirOpt = 0; // wasn't set yet
    if (dataDirOpt == 0) {
      dataHomeDirButton.setSelected(true);
      dataProgramDirButton.setSelected(false);
      dataCustomDirButton.setSelected(false);
      dataCustomDirBox.setEnabled(false);
    }
    else if (dataDirOpt == 1) {
      dataHomeDirButton.setSelected(false);
      dataProgramDirButton.setSelected(true);
      dataCustomDirButton.setSelected(false);
      dataCustomDirBox.setEnabled(false);      
    }
    else {
      dataHomeDirButton.setSelected(false);
      dataProgramDirButton.setSelected(false);
      dataCustomDirButton.setSelected(true);
      dataCustomDirBox.setEnabled(true);  
    }
    dataCustomDirBox.setText(prefs.get("CustomDataDir", "")); //$NON-NLS-1$ //$NON-NLS-2$
    messageLevelBox.setSelectedIndex(prefs.getInt("MessageLevel", 1)); //$NON-NLS-1$
    updateCheckBox.setSelected(prefs.getBoolean("CheckForUpdate", true)); //$NON-NLS-1$
    askBeforePlayerStartBox.setSelected(prefs.getBoolean("AskForPlayerStart", true)); //$NON-NLS-1$
    startPlayerBox.setSelected(prefs.getBoolean("StartLocalPlayer", true)); //$NON-NLS-1$
    keysCheckBox.setSelected(prefs.getBoolean("ShowKeys", false)); //$NON-NLS-1$
    
    boolean autoDetect = prefs.getBoolean("AutoDetectPlayer", true); //$NON-NLS-1$
    getTcpSpinner().setValue(prefs.getInt("TCPPort", 11112)); //$NON-NLS-1$
    getIpAddressField().setText(prefs.get("IPAddress", "")); //$NON-NLS-1$ //$NON-NLS-2$
    getServerNameField().setText(prefs.get("ServerName", "")); //$NON-NLS-1$ //$NON-NLS-2$
    autoManualSwitched(!autoDetect);
  }

  /**
   * This method initializes jButton1
   * 
   * @return javax.swing.JButton
   */
  private JButton getCancelButton() {
    if (jButton1 == null) {
      jButton1 = new JButton();
      jButton1.setText(Localization.getString("OptionsDialog.Cancel")); //$NON-NLS-1$
      jButton1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          dispose();
        }
      });
    }
    return jButton1;
  }

  /**
   * This method initializes jTabbedPane
   * 
   * @return javax.swing.JTabbedPane
   */
  private JTabbedPane getJTabbedPane() {
    if (jTabbedPane == null) {
      jTabbedPane = new JTabbedPane();
      jTabbedPane.addTab(Localization.getString("OptionsDialog.Connection"), null, getConnectionPanel(), null); //$NON-NLS-1$
      jTabbedPane.addTab(Localization.getString("OptionsDialog.Music"), null, getMusicPanel(), null); //$NON-NLS-1$
      jTabbedPane.addTab(Localization.getString("OptionsDialog.Program"), null, getProgramPanel(), null); //$NON-NLS-1$
      jTabbedPane.addTab(Localization.getString("OptionsDialog.Display"), null, getJPanel1(), null); //$NON-NLS-1$
    }
    return jTabbedPane;
  }
  
  private JPanel connectionPanel;
  
  private JCheckBox askBeforePlayerStartBox;
  private JCheckBox startPlayerBox;
  
  private JCheckBox getAskBeforePlayerStartBox() {
	  if (askBeforePlayerStartBox == null) {
		  askBeforePlayerStartBox = new JCheckBox(Localization.getString("OptionsDialog.AskBeforePlayerStart")); //$NON-NLS-1$
		  askBeforePlayerStartBox.setAlignmentX(LEFT_ALIGNMENT);
	  }
	  return askBeforePlayerStartBox;
  }
  
  private JCheckBox getStartPlayerBox() {
	  if (startPlayerBox == null) {
		  startPlayerBox = new JCheckBox(Localization.getString("OptionsDialog.StartLocalPlayer")); //$NON-NLS-1$
		  startPlayerBox.setAlignmentX(LEFT_ALIGNMENT);
	  }
	  return startPlayerBox;
  }
  
  private JSpinner getTcpSpinner() {
	    if (tcpSpinner == null) {
	    	tcpSpinner = new JSpinner();
	    	tcpSpinner.setModel(new SpinnerNumberModel(11112, 1000, 15000, 1));
	    }
	    return tcpSpinner;
  }

  private JTextField getServerNameField() {
	  if (serverNameField == null) {
		  serverNameField = new JTextField();
		  serverNameField.setPreferredSize(new Dimension(80, 10));
	  }
	  return serverNameField;
  }
  
  private JTextField getIpAddressField() {
	  if (addressField == null) {
	    addressField = new JTextField();
	    addressField.setPreferredSize(new Dimension(80, 10));
	  }
	  return addressField;
  }
  
  private boolean listen = true;
  
  private JRadioButton getManualSelectButton() {
	  if (manualSelectButton == null) {
		  manualSelectButton = new JRadioButton(Localization.getString("OptionsDialog.ManuallyEnterPlayer")); //$NON-NLS-1$
		  manualSelectButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
				  if (!listen)
					  return;
				  autoManualSwitched(true);
			  }
		  });
		  manualSelectButton.setAlignmentX(LEFT_ALIGNMENT);
	  }
	  return manualSelectButton;
  }
  
  private JRadioButton getAutoDetectButton() {
	  if (autoDetectButton == null) {
		  autoDetectButton = new JRadioButton(Localization.getString("OptionsDialog.AutomaticallyFindPlayer")); //$NON-NLS-1$
		  autoDetectButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
				  if (!listen)
					  return;
				  autoManualSwitched(false);
			  }
		  });
		  autoDetectButton.setAlignmentX(LEFT_ALIGNMENT);
	  }
	  return autoDetectButton;
  }
  
  private void autoManualSwitched(boolean manualSelect) {
	  listen = false;
	  getIpAddressField().setEnabled(manualSelect);
	  getTcpSpinner().setEnabled(manualSelect);
	  getServerNameField().setEnabled(manualSelect);
	  getManualSelectButton().setSelected(manualSelect);
	  getUdpSpinner().setEnabled(!manualSelect);
	  getAutoDetectButton().setSelected(!manualSelect);
	  getAskBeforePlayerStartBox().setEnabled(!manualSelect);
	  getStartPlayerBox().setEnabled(!manualSelect);
	  listen = true;
  }
  
  private JPanel getConnectionPanel() {
	  if (connectionPanel == null) {
		  connectionPanel = new JPanel();
	      connectionPanel.setLayout(new BoxLayout(connectionPanel, BoxLayout.PAGE_AXIS));
		  connectionPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	      jLabel4 = new JLabel();
	      jLabel4.setText(Localization.getString("OptionsDialog.UDPPort")); //$NON-NLS-1$
	      connectionPanel.add(getAutoDetectButton());
	      connectionPanel.add(Box.createVerticalStrut(5));
	      JPanel udpPanel = new JPanel();
	      udpPanel.setLayout(new BoxLayout(udpPanel, BoxLayout.LINE_AXIS));
	      udpPanel.add(Box.createHorizontalStrut(25));
	      udpPanel.add(jLabel4);
	      udpPanel.add(Box.createHorizontalStrut(5));
	      udpPanel.add(getUdpSpinner());
	      udpPanel.add(Box.createHorizontalGlue());
		  udpPanel.setAlignmentX(LEFT_ALIGNMENT);
	      connectionPanel.add(udpPanel);
	      connectionPanel.add(Box.createVerticalStrut(5));
	      JPanel startPlayerPanel = new JPanel();
	      startPlayerPanel.setLayout(new BoxLayout(startPlayerPanel, BoxLayout.LINE_AXIS));
	      startPlayerPanel.add(Box.createHorizontalStrut(25));
	      startPlayerPanel.add(getStartPlayerBox());
	      startPlayerPanel.setAlignmentX(LEFT_ALIGNMENT);
	      connectionPanel.add(startPlayerPanel);
	      connectionPanel.add(Box.createVerticalStrut(5));
	      JPanel startPlayerPanel2 = new JPanel();
	      startPlayerPanel2.setLayout(new BoxLayout(startPlayerPanel2, BoxLayout.LINE_AXIS));
	      startPlayerPanel2.add(Box.createHorizontalStrut(25));
	      startPlayerPanel2.add(getAskBeforePlayerStartBox());
	      startPlayerPanel2.setAlignmentX(LEFT_ALIGNMENT);
	      connectionPanel.add(startPlayerPanel2);
	      connectionPanel.add(Box.createVerticalStrut(5));
	      connectionPanel.add(getManualSelectButton());
	      connectionPanel.add(Box.createVerticalStrut(5));
	      JPanel tcpPanel = new JPanel();
	      tcpPanel.setLayout(new BoxLayout(tcpPanel, BoxLayout.LINE_AXIS));
	      tcpPanel.add(Box.createHorizontalStrut(25));
	      JLabel label5 = new JLabel();
	      label5.setText(Localization.getString("OptionsDialog.Address")); //$NON-NLS-1$
	      tcpPanel.add(label5);
	      tcpPanel.add(Box.createHorizontalStrut(5));
	      tcpPanel.add(getIpAddressField());
	      tcpPanel.add(Box.createHorizontalStrut(5));
	      JLabel label6 = new JLabel();
	      label6.setText(Localization.getString("OptionsDialog.Port")); //$NON-NLS-1$
	      tcpPanel.add(label6);
	      tcpPanel.add(Box.createHorizontalStrut(5));
	      tcpPanel.add(getTcpSpinner());
		  tcpPanel.setAlignmentX(LEFT_ALIGNMENT);
	      connectionPanel.add(tcpPanel);
	      connectionPanel.add(Box.createVerticalStrut(5));
	      JPanel serverNamePanel = new JPanel();
	      serverNamePanel.setLayout(new BoxLayout(serverNamePanel, BoxLayout.LINE_AXIS));
	      serverNamePanel.add(Box.createHorizontalStrut(25));
	      JLabel label7 = new JLabel();
	      label7.setText(Localization.getString("OptionsDialog.Name")); //$NON-NLS-1$
	      serverNamePanel.add(label7);
	      serverNamePanel.add(Box.createHorizontalStrut(5));
	      serverNamePanel.add(getServerNameField());
		  serverNamePanel.setAlignmentX(LEFT_ALIGNMENT);
	      connectionPanel.add(serverNamePanel);
	  }
	  return connectionPanel;
  }
  
  private JPanel musicPanel;
  
  private boolean listenToFadeButtons = true;
  
  private void updateFadeOptions() {
	  if (!listenToFadeButtons)
		  return;
	  listenToFadeButtons = false;
	  getFadeTimeSpinner().setEnabled(musicFadingOption != 0);
	  getNoFadeButton().setSelected(musicFadingOption == 0);
	  getFadeButton().setSelected(musicFadingOption == 1);
	  getCrossFadeButton().setSelected(musicFadingOption == 2);
	  listenToFadeButtons = true;
  }
  
  private JCheckBox getMusicOnAllSpeakersBox() {
	  if (musicOnAllSpeakersBox == null) {
		  musicOnAllSpeakersBox = new JCheckBox(Localization.getString("OptionsDialog.OnAllSpeakers")); //$NON-NLS-1$
	  }
	  return musicOnAllSpeakersBox;
  }
  
  private JRadioButton getNoFadeButton() {
	  if (noFadeButton == null) {
		  noFadeButton = new JRadioButton(Localization.getString("OptionsDialog.NoFading")); //$NON-NLS-1$
		  noFadeButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
				  if (noFadeButton.isSelected()) {
					  musicFadingOption = 0;
				  }
				  updateFadeOptions();
			  }
		  });
	  }
	  return noFadeButton;
  }
  
  private JRadioButton getFadeButton() {
	  if (fadeButton == null) {
		  fadeButton = new JRadioButton(Localization.getString("OptionsDialog.FadeOutIn")); //$NON-NLS-1$
		  fadeButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
				  if (fadeButton.isSelected())
					  musicFadingOption = 1;
				  updateFadeOptions();
			  }
		  });
	  }
	  return fadeButton;
  }
  
  private JRadioButton getCrossFadeButton() {
	  if (crossFadeButton == null) {
		  crossFadeButton = new JRadioButton(Localization.getString("OptionsDialog.Crossfading")); //$NON-NLS-1$
		  crossFadeButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
				  if (crossFadeButton.isSelected())
					  musicFadingOption = 2;
				  updateFadeOptions();
			  }
		  });
	  }
	  return crossFadeButton;
  }
  
  private JSpinner getFadeTimeSpinner() {
	  if (fadeTimeSpinner == null) {
		  fadeTimeSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 30000, 100));
		  fadeTimeSpinner.setPreferredSize(new Dimension(50, 10));
	  }
	  return fadeTimeSpinner;
  }
  
  private JPanel getMusicPanel() {
	  if (musicPanel == null) {
		  musicPanel = new JPanel();
		  musicPanel.setLayout(new BoxLayout(musicPanel, BoxLayout.PAGE_AXIS));
		  musicPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		  JCheckBox checkBox = getMusicOnAllSpeakersBox();
		  checkBox.setAlignmentX(LEFT_ALIGNMENT);
		  musicPanel.add(checkBox);
		  JLabel l1 = new JLabel(Localization.getString("OptionsDialog.FadingIntro")); //$NON-NLS-1$
		  l1.setAlignmentX(LEFT_ALIGNMENT);
		  musicPanel.add(l1);
		  JPanel p1 = new JPanel();
		  p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));
		  p1.add(Box.createHorizontalStrut(20));
		  p1.add(getNoFadeButton());
		  p1.setAlignmentX(LEFT_ALIGNMENT);
		  musicPanel.add(p1);
		  JPanel p2 = new JPanel();
		  p2.setLayout(new BoxLayout(p2, BoxLayout.LINE_AXIS));
		  p2.add(Box.createHorizontalStrut(20));
		  p2.add(getFadeButton());
		  p2.setAlignmentX(LEFT_ALIGNMENT);
		  musicPanel.add(p2);
		  JPanel p3 = new JPanel();
		  p3.setLayout(new BoxLayout(p3, BoxLayout.LINE_AXIS));
		  p3.add(Box.createHorizontalStrut(20));
		  p3.add(getCrossFadeButton());
		  p3.setAlignmentX(LEFT_ALIGNMENT);
		  musicPanel.add(p3);
		  JPanel p4 = new JPanel();
		  p4.setLayout(new BoxLayout(p4, BoxLayout.LINE_AXIS));
		  p4.add(Box.createHorizontalStrut(20));
		  p4.add(new JLabel(Localization.getString("OptionsDialog.FadingTime"))); //$NON-NLS-1$
		  p4.add(Box.createHorizontalStrut(5));
		  p4.add(getFadeTimeSpinner());
		  p4.add(Box.createHorizontalStrut(5));
		  p4.add(new JLabel(Localization.getString("OptionsDialog.Ms"))); //$NON-NLS-1$
		  p4.add(Box.createHorizontalGlue());
		  p4.setAlignmentX(LEFT_ALIGNMENT);
		  musicPanel.add(p4);
		  musicPanel.add(Box.createVerticalGlue());
	  }
	  return musicPanel;
  }

  /**
   * This method initializes jPanel1
   * 
   * @return javax.swing.JPanel
   */
  private JPanel getJPanel1() {
    if (jPanel1 == null) {
      jLabel6 = new JLabel();
      jLabel6.setBounds(new Rectangle(10, 70, 91, 21));
      jLabel6.setText(Localization.getString("OptionsDialog.Font")); //$NON-NLS-1$
      jLabel5 = new JLabel();
      jLabel5.setBounds(new Rectangle(10, 160, 141, 21));
      jLabel5.setText(Localization.getString("OptionsDialog.MessageLevel")); //$NON-NLS-1$
      jLabel2 = new JLabel();
      jLabel2.setBounds(new Rectangle(10, 40, 96, 21));
      jLabel2.setText(Localization.getString("OptionsDialog.Skin")); //$NON-NLS-1$
      jLabel1 = new JLabel();
      jLabel1.setBounds(new Rectangle(10, 100, 266, 15));
      jLabel1.setText(Localization.getString("OptionsDialog.RequiresRestart")); //$NON-NLS-1$
      jLabel = new JLabel();
      jLabel.setBounds(new Rectangle(10, 10, 97, 21));
      jLabel.setText(Localization.getString("OptionsDialog.LookNFeel")); //$NON-NLS-1$
      jPanel1 = new JPanel();
      jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.PAGE_AXIS));
      jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      JPanel lfPanel = new JPanel();
      GroupLayout lfLayout = new GroupLayout(lfPanel);
      lfLayout.setAutoCreateContainerGaps(true);
      lfLayout.setAutoCreateGaps(true);
      Component lfCombo = getLFCombo();
      Component skinField = getSkinField();
      Component skinButton = getSkinButton();
      Component fontField = getFontField();
      Component fontButton = getFontButton();
      lfLayout.setHorizontalGroup(lfLayout.createSequentialGroup()
          .addGroup(lfLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addComponent(jLabel)
              .addComponent(jLabel2)
              .addComponent(jLabel6))
          .addGroup(lfLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addComponent(lfCombo)
              .addComponent(skinField)
              .addComponent(fontField))
          .addGroup(lfLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addComponent(skinButton)
              .addComponent(fontButton)));
      lfLayout.setVerticalGroup(lfLayout.createSequentialGroup()
          .addGroup(lfLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel)
              .addComponent(lfCombo))
          .addGroup(lfLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel2)
              .addComponent(skinField)
              .addComponent(skinButton))
          .addGroup(lfLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel6)
              .addComponent(fontField)
              .addComponent(fontButton)));
      lfPanel.setLayout(lfLayout);
      jPanel1.add(lfPanel);
      jPanel1.add(Box.createVerticalStrut(5));
      JPanel labelPanel = new JPanel();
      labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.LINE_AXIS));
      labelPanel.add(jLabel1);
      labelPanel.add(Box.createHorizontalGlue());
      jPanel1.add(labelPanel);
      jPanel1.add(Box.createVerticalStrut(5));
      JPanel fgPanel = new JPanel();
      fgPanel.setLayout(new BoxLayout(fgPanel, BoxLayout.LINE_AXIS));
      fgPanel.add(getFGBox());
      fgPanel.add(getKeysBox());
      fgPanel.add(Box.createHorizontalGlue());
      jPanel1.add(fgPanel);
      JPanel messageLevelPanel = new JPanel();
      messageLevelPanel.setLayout(new BoxLayout(messageLevelPanel, BoxLayout.LINE_AXIS));
      messageLevelPanel.add(jLabel5);
      messageLevelPanel.add(Box.createHorizontalStrut(5));
      messageLevelPanel.add(getMessageLevelBox());
      JPanel lastPanel = new JPanel(new BorderLayout());
      lastPanel.add(messageLevelPanel, BorderLayout.NORTH);
      jPanel1.add(lastPanel);
    }
    return jPanel1;
  }

  /**
   * This method initializes jCheckBox
   * 
   * @return javax.swing.JCheckBox
   */
  private JCheckBox getFGBox() {
    if (fgBox == null) {
      fgBox = new JCheckBox();
      fgBox.setText(Localization.getString("OptionsDialog.BringToTop")); //$NON-NLS-1$
    }
    return fgBox;
  }
  
  private JCheckBox getKeysBox() {
	  if (keysCheckBox == null) {
		  keysCheckBox = new JCheckBox();
		  keysCheckBox.setText(Localization.getString("OptionsDialog.ShowKeys")); //$NON-NLS-1$
	  }
	  return keysCheckBox;
  }

  /**
   * This method initializes jComboBox
   * 
   * @return javax.swing.JComboBox
   */
  private JComboBox getLFCombo() {
    if (lfBox == null) {
      lfBox = new JComboBox();
      //lfBox.setPreferredSize(new Dimension(180, 20));
      for (String name : LookAndFeels.getLookAndFeels()) {
        lfBox.addItem(name);
      }
      lfBox.setSelectedItem(LookAndFeels.getCurrentLookAndFeel());
      lfBox.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          onLFSelected();
        }
      });
    }
    return lfBox;
  }

  /**
   * This method initializes jTextField
   * 
   * @return javax.swing.JTextField
   */
  private JTextField getSkinField() {
    if (skinField == null) {
      skinField = new JTextField();
      //skinField.setPreferredSize(new Dimension(180, 20));
      String themePack = LookAndFeels.getLastThemePack();
      File f = new File(themePack);
      if (f.exists()) {
        skinField.setText(themePack);
      }
    }
    return skinField;
  }

  /**
   * This method initializes jButton2
   * 
   * @return javax.swing.JButton
   */
  private JButton getSkinButton() {
    if (skinButton == null) {
      skinButton = new JButton();
      skinButton.setText("..."); //$NON-NLS-1$
      skinButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          selectSkin();
        }
      });
    }
    return skinButton;
  }

  protected void selectSkin() {
    JFileChooser chooser = new JFileChooser();
    File f = Directories.getLastUsedDirectory(this, "Skins"); //$NON-NLS-1$
    if (f != null) {
      chooser.setCurrentDirectory(f);
    }
    else {
      File f2 = new File("skins"); //$NON-NLS-1$
      if (f2.exists() && f2.isDirectory()) {
        chooser.setCurrentDirectory(f2);
      }
    }
    chooser.addChoosableFileFilter(new ExampleFileFilter("zip", Localization.getString("OptionsDialog.SkinPackages"))); //$NON-NLS-1$ //$NON-NLS-2$
    chooser.setMultiSelectionEnabled(false);
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      Directories
          .setLastUsedDirectory(this, "Skins", chooser.getSelectedFile()); //$NON-NLS-1$
      skinField.setText(chooser.getSelectedFile().getAbsolutePath());
    }

  }
  
  private JCheckBox getUpdateCheckBox() {
	  if (updateCheckBox == null) {
		  updateCheckBox = new JCheckBox(Localization.getString("OptionsDialog.CheckForUpdate")); //$NON-NLS-1$
	  }
	  return updateCheckBox;
  }

  /**
   * This method initializes programPanel	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getProgramPanel() {
    if (programPanel == null) {
      jLabel3 = new JLabel();
      jLabel3.setText(Localization.getString("OptionsDialog.SaveLayoutsIn")); //$NON-NLS-1$
      programPanel = new JPanel();
      programPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      programPanel.setLayout(new GridLayout(5, 1, 5, 5));
      programPanel.add(getUpdateCheckBox());
      programPanel.add(jLabel3);
      programPanel.add(getDataHomeDirButton());
      programPanel.add(getDataProgramDirButton());
      JPanel customDirPanel = new JPanel();
      customDirPanel.setLayout(new BoxLayout(customDirPanel, BoxLayout.LINE_AXIS));
      customDirPanel.add(getDataCustomDirButton());
      customDirPanel.add(getDataCustomDirBox());
      customDirPanel.add(Box.createHorizontalStrut(5));
      customDirPanel.add(getSelDataDirButton());
      JPanel p2 = new JPanel(new BorderLayout());
      p2.add(customDirPanel, BorderLayout.NORTH);
      programPanel.add(p2);
    }
    return programPanel;
  }

  private void updateDataButtonState(int sel) {
    if (sel != 0) dataHomeDirButton.setSelected(false);
    if (sel != 1) dataProgramDirButton.setSelected(false);
    if (sel != 2) dataCustomDirButton.setSelected(false);
    dataCustomDirBox.setEnabled(sel == 2);
  }

  /**
   * This method initializes dataHomeDirButton	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getDataHomeDirButton() {
    if (dataHomeDirButton == null) {
      dataHomeDirButton = new JRadioButton();
      dataHomeDirButton.setText(Localization.getString("OptionsDialog.UserHome")); //$NON-NLS-1$
      dataHomeDirButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          updateDataButtonState(0);
        }
      });
    }
    return dataHomeDirButton;
  }

  /**
   * This method initializes dataProgramDirButton	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getDataProgramDirButton() {
    if (dataProgramDirButton == null) {
      dataProgramDirButton = new JRadioButton();
      dataProgramDirButton.setText(Localization.getString("OptionsDialog.ProgramDir")); //$NON-NLS-1$
      dataProgramDirButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          updateDataButtonState(1);
        }
      });
    }
    return dataProgramDirButton;
  }

  /**
   * This method initializes dataCustomDirButton	
   * 	
   * @return javax.swing.JRadioButton	
   */
  private JRadioButton getDataCustomDirButton() {
    if (dataCustomDirButton == null) {
      dataCustomDirButton = new JRadioButton();
      dataCustomDirButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          updateDataButtonState(2);
        }
      });
    }
    return dataCustomDirButton;
  }

  /**
   * This method initializes dataCustomDirBox	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getDataCustomDirBox() {
    if (dataCustomDirBox == null) {
      dataCustomDirBox = new JTextField();
      //dataCustomDirBox.setPreferredSize(new Dimension(250, 20));
    }
    return dataCustomDirBox;
  }

  /**
   * This method initializes selDataDirButton	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getSelDataDirButton() {
    if (selDataDirButton == null) {
      selDataDirButton = new JButton();
      selDataDirButton.setText("..."); //$NON-NLS-1$
      selDataDirButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          selectDataCustomDir();
        }
      });
    }
    return selDataDirButton;
  }
  
  private void selectDataCustomDir() {
    JDirectoryChooser chooser = new JDirectoryChooser();
    chooser.setMultiSelectionEnabled(false);
    int result = chooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
      dataCustomDirBox.setText(chooser.getSelectedFile().getAbsolutePath());
      dataCustomDirButton.setSelected(true);
      updateDataButtonState(2);
    }
  }

  /**
   * This method initializes udpSpinner	
   * 	
   * @return javax.swing.JTextField	
   */
  private JSpinner getUdpSpinner() {
    if (udpSpinner == null) {
      udpSpinner = new JSpinner();
      udpSpinner.setModel(new SpinnerNumberModel(8001, 1000, 15000, 1));
    }
    return udpSpinner;
  }
  
  private static String[] messageLevels = {Localization.getString("OptionsDialog.Error"), Localization.getString("OptionsDialog.Warning"), Localization.getString("OptionsDialog.Info"), Localization.getString("OptionsDialog.Debug")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

  private JLabel jLabel6 = null;

  private JTextField fontField = null;

  private JButton fontButton = null;

  /**
   * This method initializes messageLevelBox	
   * 	
   * @return javax.swing.JComboBox	
   */
  private JComboBox getMessageLevelBox() {
    if (messageLevelBox == null) {
      messageLevelBox = new JComboBox();
      for (Message.MessageType level : Message.MessageType.values()) {
        messageLevelBox.addItem(messageLevels[level.ordinal()]);
      }
    }
    return messageLevelBox;
  }

  /**
   * This method initializes fontField	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getFontField() {
    if (fontField == null) {
      fontField = new JTextField();
      //fontField.setPreferredSize(new Dimension(180, 20));
      fontField.setEditable(false);
    }
    return fontField;
  }

  /**
   * This method initializes fontButton	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getFontButton() {
    if (fontButton == null) {
      fontButton = new JButton();
      fontButton.setText("..."); //$NON-NLS-1$
      fontButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          selectFont();
        }
      });
    }
    return fontButton;
  }
  
  private void selectFont() {
    Font newFont = JFontChooser.showDialog(this, Localization.getString("OptionsDialog.ChooseFont"), Font.decode(fontField.getText())); //$NON-NLS-1$
    if (newFont != null) {
      String fontName = newFont.getFontName();
      int fontSize = newFont.getSize();
      fontField.setText(fontName + " " + fontSize); //$NON-NLS-1$
    }
  }

} //  @jve:decl-index=0:visual-constraint="10,10"

/*
 * Copyright (C) 2022 KriolOS
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.kriolos.opos.gui;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.config.JPanelConfiguration;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppProperties;
import java.awt.BorderLayout;
import java.util.Collection;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

/**
 * Top component which displays Settings.
 */
@ConvertAsProperties(
        dtd = "-//io.github.kriolos.opos.gui//Settings//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "settingsTopComponent",
        iconBase = "io/github/kriolos/opos/gui/computer.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_SettingsAction",
        preferredID = "settingsTopComponent"
)

@ActionID(
        category = "Window",
        id = "io.github.kriolos.opos.gui.SettingsTopComponent"
)
@ActionReferences({
    @ActionReference(path = "Menu/Window", position = 0),
    //@ActionReference(path = "Toolbars/File", position = 300)
})

@Messages({
    "CTL_SettingsAction=POS Settings",
    "CTL_SettingsTopComponent=POS Settings Window",
    "HINT_SettingsTopComponent=This is POS Settings window"
})
public final class SettingsTopComponent extends TopComponent {

    private Lookup.Result result = null;
    private JPanelConfiguration configPanel;

    public SettingsTopComponent() {
        initComponents();
        setName(Bundle.CTL_SettingsTopComponent());
        setToolTipText(Bundle.HINT_SettingsTopComponent());
        putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);
        setLayout(new BorderLayout());
        setVisible(true);

        try {
            AppConfig config = new AppConfig(null);
            config.load();

            configPanel = new JPanelConfiguration(config);

            add(configPanel, BorderLayout.CENTER); 
            configPanel.setVisible(true);
            configPanel.activate();
        } catch (BasicException e) {
        }

    }

    @Override
    public void componentOpened() {
        //result = Utilities.actionsGlobalContext().lookupResult(DeviceInfo.class);
        //result.addLookupListener(this);
        configPanel.loadProperties();

    }

    @Override
    public void componentClosed() {
        //result.removeLookupListener(this);
        //result = null;
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

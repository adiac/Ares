﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Ares.Data;

namespace Ares.Editor.Controls
{
    public partial class FileEffectsControl : UserControl
    {
        public FileEffectsControl()
        {
            InitializeComponent();
        }

        private Ares.Data.IFileElement m_Element;
        private bool listen = true;
        private Ares.Data.IGeneralElementContainer m_Container;

        public void SetContainer(Ares.Data.IGeneralElementContainer container)
        {
            m_Container = container;
        }

        public void SetEffects(Ares.Data.IFileElement element)
        {
            if (m_Element != null)
            {
                Actions.ElementChanges.Instance.RemoveListener(m_Element.Id, Update);
            }
            m_Element = element;
            if (m_Element != null)
            {
                Update(m_Element.Id, Actions.ElementChanges.ChangeType.Changed);
                Actions.ElementChanges.Instance.AddListener(m_Element.Id, Update);
                pitchButton.Enabled = true;
                allPitchButton.Enabled = true;
                balanceButton.Enabled = true;
                allBalanceButton.Enabled = true;
                volumeButton.Enabled = true;
                allVolumeButton.Enabled = true;
            }
            else
            {
                pitchBox.Checked = false;
                pitchButton.Enabled = false;
                allPitchButton.Enabled = false;
                balanceButton.Enabled = false;
                allBalanceButton.Enabled = false;
                volumeButton.Enabled = false;
                allVolumeButton.Enabled = false;
            }
        }

        private void Update(int id, Actions.ElementChanges.ChangeType changeType)
        {
            if (listen && changeType == Actions.ElementChanges.ChangeType.Changed)
            {
                listen = false;
                pitchBox.Checked = m_Element.Effects.Pitch.Active;
                balanceBox.Checked = m_Element.Effects.Balance.Active;
                volumeBox.Checked = m_Element.Effects.VolumeDB.Active;
                listen = true;
            }
        }

        private void pitchBox_CheckedChanged(object sender, EventArgs e)
        {
            if (!listen)
                return;
            IIntEffect effect = m_Element.Effects.Pitch;
            Actions.Actions.Instance.AddNew(new Actions.IntEffectChangeAction(m_Element, effect,
                pitchBox.Checked, effect.Random, effect.FixValue, effect.MinRandomValue, effect.MaxRandomValue));
        }

        private void pitchButton_Click(object sender, EventArgs e)
        {
            PitchDialog dialog = new PitchDialog(m_Element);
            if (dialog.ShowDialog(Parent) == DialogResult.OK)
            {
                dialog.UpdateAction();
                Actions.Actions.Instance.AddNew(dialog.Action);
            }
        }

        private void allPitchButton_Click(object sender, EventArgs e)
        {
            IIntEffect effect = m_Element.Effects.Pitch;
            Actions.Actions.Instance.AddNew(new Actions.AllFileElementsPitchChangeAction(m_Container,
                pitchBox.Checked, effect.Random, effect.FixValue, effect.MinRandomValue, effect.MaxRandomValue));
        }

        private void balanceBox_CheckedChanged(object sender, EventArgs e)
        {
            if (!listen)
                return;
            IIntEffect effect = m_Element.Effects.Balance;
            Actions.Actions.Instance.AddNew(new Actions.IntEffectChangeAction(m_Element, effect,
                balanceBox.Checked, effect.Random, effect.FixValue, effect.MinRandomValue, effect.MaxRandomValue));
        }

        private void balanceButton_Click(object sender, EventArgs e)
        {
            BalanceDialog dialog = new BalanceDialog(m_Element);
            if (dialog.ShowDialog(Parent) == DialogResult.OK)
            {
                dialog.UpdateAction();
                Actions.Actions.Instance.AddNew(dialog.Action);
            }
        }

        private void allBalanceButton_Click(object sender, EventArgs e)
        {
            IIntEffect effect = m_Element.Effects.Balance;
            Actions.Actions.Instance.AddNew(new Actions.AllFileElementsBalanceChangeAction(m_Container,
                balanceBox.Checked, effect.Random, effect.FixValue, effect.MinRandomValue, effect.MaxRandomValue));
        }

        private void volumeBox_CheckedChanged(object sender, EventArgs e)
        {
            if (!listen)
                return;
            IIntEffect effect = m_Element.Effects.VolumeDB;
            Actions.Actions.Instance.AddNew(new Actions.IntEffectChangeAction(m_Element, effect,
                volumeBox.Checked, effect.Random, effect.FixValue, effect.MinRandomValue, effect.MaxRandomValue));
        }

        private void volumeButton_Click(object sender, EventArgs e)
        {
            VolumeDBDialog dialog = new VolumeDBDialog(m_Element);
            if (dialog.ShowDialog(Parent) == DialogResult.OK)
            {
                dialog.UpdateAction();
                Actions.Actions.Instance.AddNew(dialog.Action);
            }
        }

        private void allVolumeButton_Click(object sender, EventArgs e)
        {
            IIntEffect effect = m_Element.Effects.VolumeDB;
            Actions.Actions.Instance.AddNew(new Actions.AllFileElementsVolumeDBChangeAction(m_Container,
                volumeBox.Checked, effect.Random, effect.FixValue, effect.MinRandomValue, effect.MaxRandomValue));
        }


    }
}

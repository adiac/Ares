﻿/*
 Copyright (c) 2015  [Joerg Ruedenauer]
 
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
using System;
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
        private List<Ares.Data.IFileElement> m_ElementList;
        private bool listen = true;
        private Ares.Data.IGeneralElementContainer m_Container;
        private Ares.Data.IProject m_Project;

        public void SetContainer(Ares.Data.IGeneralElementContainer container, Ares.Data.IProject project)
        {
            m_Container = container;
            m_Project = project;
        }

        public void SetEffects(Ares.Data.IFileElement element)
        {
            if (m_Element != null)
            {
                Actions.ElementChanges.Instance.RemoveListener(m_Element.Id, Update);
            }
            m_Element = element;
            m_ElementList = new List<IFileElement>();
            m_ElementList.Add(m_Element);
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
                speakerButton.Enabled = true;
                allSpeakerButton.Enabled = true;
                reverbButton.Enabled = true;
                allReverbButton.Enabled = true;
                tempoButton.Enabled = true;
                allTempoButton.Enabled = true;
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
                speakerButton.Enabled = false;
                allSpeakerButton.Enabled = false;
                reverbButton.Enabled = false;
                allReverbButton.Enabled = false;
                tempoButton.Enabled = false;
                allTempoButton.Enabled = false;
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
                speakerBox.Checked = m_Element.Effects.SpeakerAssignment.Active;
                reverbBox.Checked = m_Element.Effects.Reverb.Active;
                tempoBox.Checked = m_Element.Effects.Tempo.Active;
                listen = true;
            }
        }

        private void pitchBox_CheckedChanged(object sender, EventArgs e)
        {
            if (!listen)
                return;
            IIntEffect effect = m_Element.Effects.Pitch;
            List<IIntEffect> effects = new List<IIntEffect>();
            effects.Add(effect);
            Actions.Actions.Instance.AddNew(new Actions.IntEffectChangeAction(m_ElementList, effects,
                pitchBox.Checked, effect.Random, effect.FixValue, effect.MinRandomValue, effect.MaxRandomValue), m_Project);
        }

        private void pitchButton_Click(object sender, EventArgs e)
        {
            PitchDialog dialog = new PitchDialog(m_ElementList, m_Project);
            if (dialog.ShowDialog(Parent) == DialogResult.OK)
            {
                dialog.UpdateAction();
                Actions.Actions.Instance.AddNew(dialog.Action, m_Project);
            }
        }

        private void allPitchButton_Click(object sender, EventArgs e)
        {
            IIntEffect effect = m_Element.Effects.Pitch;
            Actions.Actions.Instance.AddNew(new Actions.AllFileElementsPitchChangeAction(m_Container,
                pitchBox.Checked, effect.Random, effect.FixValue, effect.MinRandomValue, effect.MaxRandomValue), m_Project);
        }

        private void balanceBox_CheckedChanged(object sender, EventArgs e)
        {
            if (!listen)
                return;
            Actions.Actions.Instance.AddNew(new Actions.BalanceChangeAction(m_ElementList, balanceBox.Checked), m_Project);
        }

        private void balanceButton_Click(object sender, EventArgs e)
        {
            BalanceDialog dialog = new BalanceDialog(m_ElementList, m_Project);
            if (dialog.ShowDialog(Parent) == DialogResult.OK)
            {
                dialog.UpdateAction();
                Actions.Actions.Instance.AddNew(dialog.Action, m_Project);
            }
        }

        private void allBalanceButton_Click(object sender, EventArgs e)
        {
            IBalanceEffect effect = m_Element.Effects.Balance;
            Actions.Actions.Instance.AddNew(new Actions.AllFileElementsBalanceChangeAction(m_Container, effect), m_Project);
        }

        private void volumeBox_CheckedChanged(object sender, EventArgs e)
        {
            if (!listen)
                return;
            IIntEffect effect = m_Element.Effects.VolumeDB;
            List<IIntEffect> effects = new List<IIntEffect>();
            effects.Add(effect);
            Actions.Actions.Instance.AddNew(new Actions.IntEffectChangeAction(m_ElementList, effects,
                volumeBox.Checked, effect.Random, effect.FixValue, effect.MinRandomValue, effect.MaxRandomValue), m_Project);
        }

        private void volumeButton_Click(object sender, EventArgs e)
        {
            VolumeDBDialog dialog = new VolumeDBDialog(m_ElementList, m_Project);
            if (dialog.ShowDialog(Parent) == DialogResult.OK)
            {
                dialog.UpdateAction();
                Actions.Actions.Instance.AddNew(dialog.Action, m_Project);
            }
        }

        private void allVolumeButton_Click(object sender, EventArgs e)
        {
            IIntEffect effect = m_Element.Effects.VolumeDB;
            Actions.Actions.Instance.AddNew(new Actions.AllFileElementsVolumeDBChangeAction(m_Container,
                volumeBox.Checked, effect.Random, effect.FixValue, effect.MinRandomValue, effect.MaxRandomValue), m_Project);
        }

        private void speakerBox_CheckedChanged(object sender, EventArgs e)
        {
            if (!listen)
                return;
            ISpeakerAssignmentEffect effect = m_Element.Effects.SpeakerAssignment;
            Actions.Actions.Instance.AddNew(new Actions.SpeakerChangeAction(m_ElementList, speakerBox.Checked,
                effect.Random, effect.Assignment), m_Project);
        }

        private void speakerButton_Click(object sender, EventArgs e)
        {
            SpeakersDialog dialog = new SpeakersDialog(m_ElementList, m_Project);
            if (dialog.ShowDialog(Parent) == DialogResult.OK)
            {
                dialog.UpdateAction();
                Actions.Actions.Instance.AddNew(dialog.Action, m_Project);
            }
        }

        private void allSpeakerButton_Click(object sender, EventArgs e)
        {
            ISpeakerAssignmentEffect effect = m_Element.Effects.SpeakerAssignment;
            Actions.Actions.Instance.AddNew(new Actions.AllFileElementsSpeakerChangeAction(m_Container,
                speakerBox.Checked, effect.Random, effect.Assignment), m_Project);
        }

        private void reverbBox_CheckedChanged(object sender, EventArgs e)
        {
            if (!listen)
                return;
            Actions.Actions.Instance.AddNew(new Actions.ReverbEffectChangeAction(m_ElementList, reverbBox.Checked), m_Project);
        }

        private void reverbButton_Click(object sender, EventArgs e)
        {
            ReverbDialog dialog = new ReverbDialog(m_ElementList, m_Project);
            if (dialog.ShowDialog(Parent) == DialogResult.OK)
            {
                dialog.UpdateAction();
                Actions.Actions.Instance.AddNew(dialog.Action, m_Project);
            }
        }

        private void allReverbButton_Click(object sender, EventArgs e)
        {
            Actions.Actions.Instance.AddNew(new Actions.AllFileElementsReverbChangeAction(m_Container,
                m_Element.Effects.Reverb), m_Project);
        }

        private void tempoButton_Click(object sender, EventArgs e)
        {
            TempoDialog dialog = new TempoDialog(m_ElementList, m_Project);
            if (dialog.ShowDialog(Parent) == DialogResult.OK)
            {
                dialog.UpdateAction();
                Actions.Actions.Instance.AddNew(dialog.Action, m_Project);
            }
        }

        private void allTempoButton_Click(object sender, EventArgs e)
        {
            IIntEffect effect = m_Element.Effects.Tempo;
            Actions.Actions.Instance.AddNew(new Actions.AllFileElementsTempoChangeAction(m_Container,
                pitchBox.Checked, effect.Random, effect.FixValue, effect.MinRandomValue, effect.MaxRandomValue), m_Project);
        }

        private void tempoBox_CheckedChanged(object sender, EventArgs e)
        {
            if (!listen)
                return;
            IIntEffect effect = m_Element.Effects.Tempo;
            List<IIntEffect> effects = new List<IIntEffect>();
            effects.Add(effect);
            Actions.Actions.Instance.AddNew(new Actions.IntEffectChangeAction(m_ElementList, effects,
                tempoBox.Checked, effect.Random, effect.FixValue, effect.MinRandomValue, effect.MaxRandomValue), m_Project);
        }
    }
}

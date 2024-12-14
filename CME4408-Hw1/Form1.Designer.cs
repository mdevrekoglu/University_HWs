namespace nlp_ngram
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            fileSelectBtn = new Button();
            selectedFilePathText = new TextBox();
            analyzeBtn = new Button();
            ngramTable = new DataGridView();
            typeNgram = new ComboBox();
            showResultBtn = new Button();
            analyzeTimeText = new TextBox();
            analyzeTimeLabel = new Label();
            ((System.ComponentModel.ISupportInitialize)ngramTable).BeginInit();
            SuspendLayout();
            // 
            // fileSelectBtn
            // 
            fileSelectBtn.Location = new Point(12, 41);
            fileSelectBtn.Name = "fileSelectBtn";
            fileSelectBtn.Size = new Size(75, 23);
            fileSelectBtn.TabIndex = 0;
            fileSelectBtn.Text = "Select File";
            fileSelectBtn.UseVisualStyleBackColor = true;
            fileSelectBtn.Click += button1_Click;
            // 
            // selectedFilePathText
            // 
            selectedFilePathText.Location = new Point(12, 12);
            selectedFilePathText.Name = "selectedFilePathText";
            selectedFilePathText.ReadOnly = true;
            selectedFilePathText.Size = new Size(776, 23);
            selectedFilePathText.TabIndex = 1;
            // 
            // analyzeBtn
            // 
            analyzeBtn.Enabled = false;
            analyzeBtn.Location = new Point(700, 43);
            analyzeBtn.Name = "analyzeBtn";
            analyzeBtn.Size = new Size(75, 23);
            analyzeBtn.TabIndex = 2;
            analyzeBtn.Text = "Analyze";
            analyzeBtn.UseVisualStyleBackColor = true;
            analyzeBtn.Click += button1_Click_1;
            // 
            // ngramTable
            // 
            ngramTable.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            ngramTable.Location = new Point(12, 86);
            ngramTable.Name = "ngramTable";
            ngramTable.Size = new Size(776, 352);
            ngramTable.TabIndex = 3;
            // 
            // typeNgram
            // 
            typeNgram.DropDownStyle = ComboBoxStyle.DropDownList;
            typeNgram.FormattingEnabled = true;
            typeNgram.Items.AddRange(new object[] { "Unigram", "Bigram", "Trigram" });
            typeNgram.Location = new Point(125, 41);
            typeNgram.Name = "typeNgram";
            typeNgram.Size = new Size(121, 23);
            typeNgram.TabIndex = 6;
            typeNgram.SelectedIndex = 0;
            typeNgram.SelectedIndexChanged += typeNgram_SelectedIndexChanged;
            // 
            // showResultBtn
            // 
            showResultBtn.Enabled = false;
            showResultBtn.Location = new Point(270, 42);
            showResultBtn.Name = "showResultBtn";
            showResultBtn.Size = new Size(105, 23);
            showResultBtn.TabIndex = 5;
            showResultBtn.Text = "Show Result";
            showResultBtn.UseVisualStyleBackColor = true;
            showResultBtn.Click += button1_Click_2;
            // 
            // analyzeTimeText
            // 
            analyzeTimeText.Location = new Point(572, 43);
            analyzeTimeText.Name = "analyzeTimeText";
            analyzeTimeText.ReadOnly = true;
            analyzeTimeText.Size = new Size(100, 23);
            analyzeTimeText.TabIndex = 7;
            analyzeTimeText.TextChanged += textBox1_TextChanged;
            // 
            // analyzeTimeLabel
            // 
            analyzeTimeLabel.AutoSize = true;
            analyzeTimeLabel.Location = new Point(462, 49);
            analyzeTimeLabel.Name = "analyzeTimeLabel";
            analyzeTimeLabel.Size = new Size(104, 15);
            analyzeTimeLabel.TabIndex = 8;
            analyzeTimeLabel.Text = "Analyze Time (ms)";
            analyzeTimeLabel.Click += label1_Click;
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(800, 450);
            Controls.Add(analyzeTimeLabel);
            Controls.Add(analyzeTimeText);
            Controls.Add(showResultBtn);
            Controls.Add(typeNgram);
            Controls.Add(ngramTable);
            Controls.Add(analyzeBtn);
            Controls.Add(selectedFilePathText);
            Controls.Add(fileSelectBtn);
            Name = "Form1";
            Text = "Form1";
            Load += Form1_Load;
            ((System.ComponentModel.ISupportInitialize)ngramTable).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private Button fileSelectBtn;
        private TextBox selectedFilePathText;
        private Button analyzeBtn;
        private DataGridView ngramTable;
        private ComboBox typeNgram;
        private Button showResultBtn;
        private TextBox analyzeTimeText;
        private Label analyzeTimeLabel;
    }
}

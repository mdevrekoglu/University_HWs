using System.Data;

namespace nlp_ngram
{
    public partial class Form1 : Form
    {
        public bool txtSelected = false;
        public Markov nlp;

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
        }

        private void button1_Click(object sender, EventArgs e)
        {
            OpenFileDialog ofd = new OpenFileDialog();
            ofd.Filter = "txt files (*.txt)|*.txt|All files (*.*)|*.*";

            if (ofd.ShowDialog() == DialogResult.OK)
            {
                selectedFilePathText.Text = ofd.FileName;
                txtSelected = true;
                analyzeBtn.Enabled = true;
            }
            else
            {
                selectedFilePathText.Text = "";
                txtSelected = false;
                analyzeBtn.Enabled = false;
            }
            showResultBtn.Enabled = false;
            ngramTable.DataSource = null;
            analyzeTimeText.Text = "";
        }

        private void button1_Click_1(object sender, EventArgs e)
        {
            nlp = new Markov(selectedFilePathText.Text);

            long analyzeTimeInMs = nlp.UseNGram();

            analyzeTimeText.Text = analyzeTimeInMs.ToString();
            analyzeBtn.Enabled = false;
            showResultBtn.Enabled = true;
        }

        private void typeNgram_SelectedIndexChanged(object sender, EventArgs e)
        {
        }

        private void button1_Click_2(object sender, EventArgs e)
        {
            var n = 50;

            DataTable dt = new DataTable();
            dt.Columns.Add("Index");
            dt.Columns.Add("NGram");
            dt.Columns.Add("Probability");

            if (typeNgram.SelectedIndex == 0) // Unigram
            {
                int index = 1;
                var unigramResult = nlp.GetUnigramProbabilities(n);

                foreach (var unigram in unigramResult)
                {
                    DataRow dr = dt.NewRow();
                    dr["Index"] = index++;
                    dr["NGram"] = unigram.Key;
                    dr["Probability"] = unigram.Value.ToString("0.0000000000");
                    dt.Rows.Add(dr);
                }
            }
            else if (typeNgram.SelectedIndex == 1) // Bigram
            {
                int index = 1;
                var bigramResult = nlp.GetBigramProbabilities(n);

                foreach (var bigram in bigramResult)
                {
                    DataRow dr = dt.NewRow();
                    dr["Index"] = index++;
                    dr["NGram"] = bigram.Key;
                    dr["Probability"] = bigram.Value.ToString("0.0000000000");
                    dt.Rows.Add(dr);
                }
            }
            else // Trigram
            {
                int index = 1;
                var trigramResult = nlp.GetTrigramProbabilities(n);

                foreach (var trigram in trigramResult)
                {
                    DataRow dr = dt.NewRow();
                    dr["Index"] = index++;
                    dr["NGram"] = trigram.Key;
                    dr["Probability"] = trigram.Value.ToString("0.0000000000");
                    dt.Rows.Add(dr);
                }
            }

            ngramTable.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;

            ngramTable.DataSource = dt;
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
        }

        private void label1_Click(object sender, EventArgs e)
        {
        }
    }
}
using System.Diagnostics;
using System.Text;
using System.Text.RegularExpressions;

namespace nlp_ngram
{
    public class Markov
    {
        private readonly string _path;
        private readonly List<string> _words;
        private bool flag;

        private Dictionary<string, int> _unigramCounts = new Dictionary<string, int>();
        private Dictionary<string, int> _bigramCounts = new Dictionary<string, int>();
        private Dictionary<string, int> _trigramCounts = new Dictionary<string, int>();

        public record ResultClass(string Key, double Value);

        private List<ResultClass> _unigramProbabilities = new List<ResultClass>();
        private List<ResultClass> _bigramProbabilities = new List<ResultClass>();
        private List<ResultClass> _trigramProbabilities = new List<ResultClass>();

        public Markov(string path)
        {
            _path = path;
            _words = ReadTxt();
            flag = false;
        }

        private double BinarySearch(List<ResultClass> list, string key)
        {
            int left = 0;
            int right = list.Count - 1;

            while (left <= right)
            {
                int mid = left + (right - left) / 2;

                int comparison = string.Compare(list[mid].Key, key);

                if (comparison == 0)
                {
                    return list[mid].Value;
                }
                else if (comparison < 0)
                {
                    left = mid + 1;
                }
                else
                {
                    right = mid - 1;
                }
            }

            throw new Exception("Key not found, error");
        }

        public long UseNGram()
        {
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.Start();

            for (int i = 0; i < _words.Count; i++)
            {
                // Unigram
                string unigram = _words[i];
                AddToDictionary(_unigramCounts, unigram);

                // Bigram
                if (i < _words.Count - 1)
                {
                    string bigram = _words[i] + " " + _words[i + 1];
                    AddToDictionary(_bigramCounts, bigram);
                }

                // Trigram
                if (i < _words.Count - 2)
                {
                    string trigram = _words[i] + " " + _words[i + 1] + " " + _words[i + 2];
                    AddToDictionary(_trigramCounts, trigram);
                }
            }

            var totalUnigrams = _unigramCounts.Values.Sum();

            _unigramProbabilities = _unigramCounts.OrderBy(p => p.Key)
                                    .Select(p => new ResultClass(p.Key, (double)p.Value / totalUnigrams))
                                    .ToList();

            _bigramProbabilities = _bigramCounts
                                    .Select(p =>
                                    {
                                        var words = p.Key.Split(' ');
                                        string firstWord = words[0];
                                        double probability = BinarySearch(_unigramProbabilities, firstWord) * (double)p.Value / _unigramCounts[firstWord];
                                        return new ResultClass(p.Key, probability);
                                    })
                                    .OrderBy(p => p.Key)
                                    .ToList();

            _trigramProbabilities = _trigramCounts
                                    .Select(p =>
                                    {
                                        var words = p.Key.Split(' ');
                                        string firstWord = words[0];
                                        string firstTwoWords = $"{words[0]} {words[1]}";
                                        string lastTwoWords = $"{words[1]} {words[2]}";
                                        double probability = BinarySearch(_unigramProbabilities, firstWord) * BinarySearch(_bigramProbabilities, firstTwoWords)
                                                             * BinarySearch(_bigramProbabilities, lastTwoWords);
                                        return new ResultClass(p.Key, probability);
                                    })
                                    .OrderBy(p => p.Key)
                                    .ToList();
            stopwatch.Stop();

            flag = true;

            return stopwatch.ElapsedMilliseconds;
        }

        private void AddToDictionary(Dictionary<string, int> dict, string key)
        {
            if (dict.ContainsKey(key))
                dict[key]++;
            else
                dict[key] = 1;
        }

        public List<ResultClass> GetUnigramProbabilities(int n)
        {
            if (!flag) throw new Exception("Model isn't trained.");

            return _unigramProbabilities.OrderByDescending(p => p.Value).Take(n).ToList();
        }

        public List<ResultClass> GetBigramProbabilities(int n)
        {
            if (!flag) throw new Exception("Model isn't trained.");

            return _bigramProbabilities.OrderByDescending(p => p.Value).Take(n).ToList();
        }

        public List<ResultClass> GetTrigramProbabilities(int n)
        {
            if (!flag) throw new Exception("Model isn't trained.");

            return _trigramProbabilities.OrderByDescending(p => p.Value).Take(n).ToList();
        }

        private List<string> ReadTxt()
        {
            string body = string.Empty;
            Encoding.RegisterProvider(CodePagesEncodingProvider.Instance);

            // Read ANSI encoded text file
            using (StreamReader sr = new StreamReader(_path, Encoding.GetEncoding(1254)))
            {
                body = sr.ReadToEnd();
            }

            // Clean and tokenize text
            body = Regex.Replace(body, @"[^\w\s]|[\d]", "");
            body = body.Replace("\r", "").Replace("\n", "").Replace("\t", "").ToLower();

            return body.Split(new[] { ' ' }, StringSplitOptions.RemoveEmptyEntries).ToList();
        }
    }
}
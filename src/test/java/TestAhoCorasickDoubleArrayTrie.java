/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2015/4/6 12:42</create-date>
 *
 * <copyright file="TestAhoCorasickDoubleArrayTrie.java" company="�Ϻ���ԭ��Ϣ�Ƽ����޹�˾">
 * Copyright (c) 2003-2014, �Ϻ���ԭ��Ϣ�Ƽ����޹�˾. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact �Ϻ���ԭ��Ϣ�Ƽ����޹�˾ to get more information.
 * </copyright>
 */

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import junit.framework.TestCase;
import org.ahocorasick.trie.Trie;

import java.io.*;
import java.util.*;

/**
 * @author hankcs
 */
public class TestAhoCorasickDoubleArrayTrie extends TestCase
{
    private AhoCorasickDoubleArrayTrie<String> buildASimpleAhoCorasickDoubleArrayTrie()
    {
        // Collect test data set
        TreeMap<String, String> map = new TreeMap<String, String>();
        String[] keyArray = new String[]
                {
                        "hers",
                        "his",
                        "she",
                        "he"
                };
        for (String key : keyArray)
        {
            map.put(key, key);
        }
        // Build an AhoCorasickDoubleArrayTrie
        AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<String>();
        acdat.build(map);
        return acdat;
    }

    private void validateASimpleAhoCorasickDoubleArrayTrie(AhoCorasickDoubleArrayTrie<String> acdat)
    {
        // Test it
        final String text = "uhers";
        acdat.parseText(text, new AhoCorasickDoubleArrayTrie.IHit<String>()
        {
            @Override
            public void hit(int begin, int end, String value)
            {
                System.out.printf("[%d:%d]=%s\n", begin, end, value);
                assertEquals(text.substring(begin, end), value);
            }
        });
        // Or simply use
        List<AhoCorasickDoubleArrayTrie<String>.Hit<String>> wordList = acdat.parseText(text);
        System.out.println(wordList);
    }


    private AhoCorasickDoubleArrayTrie<String> buildASimpleAhoCorasickDoubleArrayTrieChinese()
    {
        // Collect test data set
        TreeMap<String, String> map = new TreeMap<String, String>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("D:\\project\\github\\AhoCorasickDoubleArrayTrie\\src\\test\\resources\\cn\\dict.txt"));
            String line ;

            while ((line = reader.readLine())!=null){
                map.put(line, line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

 /*       String[] keyArray = new String[]
                {       "京东",
                        "刘强东",
                        "奶茶",
                        "迷汗药",
                        "东京热",
                        "仓井玛利亚"
                };
        for (String key : keyArray)
        {
            map.put(key, key);
        }*/
        // Build an AhoCorasickDoubleArrayTrie
        AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<String>();
        acdat.build(map);
        return acdat;
    }

    private void validateASimpleAhoCorasickDoubleArrayTrieChinese(AhoCorasickDoubleArrayTrie<String> acdat)
    {
        // Test it
        final String text = "仓井玛利亚在中国大行其道,奶茶妹妹在京东见过么兄弟们";
        int  sum = 1000;

        long start = System.currentTimeMillis();
        for (int i = 0; i < sum; i++) {
            acdat.parseText(Text.TEXT, new AhoCorasickDoubleArrayTrie.IHit<String>()
            {
                @Override
                public void hit(int begin, int end, String value)
                {
                    System.out.printf("[%d:%d]=%s\n", begin, end, value);
                }
            });
        }
        System.out.println("cost time :" + (System.currentTimeMillis() - start) + "ms");
        System.out.println("cost time :" + (System.currentTimeMillis() - start) * 1000/sum  +  " ns");


        acdat.parseText(" taobao ", new AhoCorasickDoubleArrayTrie.IHit<String>()
        {
            @Override
            public void hit(int begin, int end, String value)
            {
                System.out.printf("[%d:%d]=%s\n", begin, end, value);
            }
        });
        // Or simply use
//        List<AhoCorasickDoubleArrayTrie<String>.Hit<String>> wordList = acdat.parseText(text);
//        System.out.println(wordList);


     /*   // Test it
        final String text1 = "夜空中最亮的星请指引我靠近你";
        acdat.parseText(text1, new AhoCorasickDoubleArrayTrie.IHit<String>()
        {
            @Override
            public void hit(int begin, int end, String value)
            {
                System.out.printf("[%d:%d]=%s\n", begin, end, value);
                assertEquals(text.substring(begin, end), value);
            }
        });
        // Or simply use
        List<AhoCorasickDoubleArrayTrie<String>.Hit<String>> wordList1 = acdat.parseText(text1);
        System.out.println(wordList1);*/
    }


    public void testBuildAndParseSimplyChinese() throws Exception
    {
        AhoCorasickDoubleArrayTrie<String> acdat = buildASimpleAhoCorasickDoubleArrayTrieChinese();
        validateASimpleAhoCorasickDoubleArrayTrieChinese(acdat);
    }


    public void testBuildAndParseSimply() throws Exception
    {
        AhoCorasickDoubleArrayTrie<String> acdat = buildASimpleAhoCorasickDoubleArrayTrie();
        validateASimpleAhoCorasickDoubleArrayTrie(acdat);
    }

    public void testBuildAndParseWithBigFile() throws Exception
    {
        // Load test data from disk
        Set<String> dictionary = loadDictionary("cn/dictionary.txt");
        final String text = loadText("cn/text.txt");
        // You can use any type of Map to hold data
        Map<String, String> map = new TreeMap<String, String>();
//        Map<String, String> map = new HashMap<String, String>();
//        Map<String, String> map = new LinkedHashMap<String, String>();
        for (String key : dictionary)
        {
            map.put(key, key);
        }
        // Build an AhoCorasickDoubleArrayTrie
        AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<String>();
        acdat.build(map);

        List<AhoCorasickDoubleArrayTrie<String>.Hit<String>> wordList =
                acdat.parseText("我在长江之南的某个小平原上抖抖索索地划拉着一盒火柴，但总是因无力而过度用力，结果不仅弄断了火柴梗子，还让满盒的火柴干戈寥落撒了半地。我只好又从脚下去捡那一地的火柴梗。");

        System.out.println(wordList);

      /*  // Test it
        acdat.parseText(text, new AhoCorasickDoubleArrayTrie.IHit<String>()
        {
            @Override
            public void hit(int begin, int end, String value)
            {
                System.out.printf("[%d:%d]=%s\n", begin, end, value);
                assertEquals(text.substring(begin, end), value);
            }
        });

        List<AhoCorasickDoubleArrayTrie<String>.Hit<String>> wordList =  acdat.parseText(text);
        System.out.println(wordList);*/

    }

    private String loadText(String path) throws IOException
    {
        StringBuilder sbText = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(path), "UTF-8"));
        String line;
        while ((line = br.readLine()) != null)
        {
            sbText.append(line).append("\n");
        }
        br.close();

        return sbText.toString();
    }

    private Set<String> loadDictionary(String path) throws IOException
    {
        Set<String> dictionary = new TreeSet<String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(path), "UTF-8"));
        String line;
        while ((line = br.readLine()) != null)
        {
            dictionary.add(line);
        }
        br.close();

        return dictionary;
    }

    private void runTest(String dictionaryPath, String textPath) throws IOException
    {
        Set<String> dictionary = loadDictionary(dictionaryPath);
        String text = loadText(textPath);
        // Build a ahoCorasickNaive implemented by robert-bor
        Trie ahoCorasickNaive = new Trie();
        for (String word : dictionary)
        {
            ahoCorasickNaive.addKeyword(word);
        }
        ahoCorasickNaive.parseText(""); // More fairly, robert-bor's implementation needs to call this to build ac automata.
        // Build a AhoCorasickDoubleArrayTrie implemented by hankcs
        AhoCorasickDoubleArrayTrie<String> ahoCorasickDoubleArrayTrie = new AhoCorasickDoubleArrayTrie<String>();
        TreeMap<String, String> dictionaryMap = new TreeMap<String, String>();
        for (String word : dictionary)
        {
            dictionaryMap.put(word, word);  // we use the same text as the property of a word
        }
        ahoCorasickDoubleArrayTrie.build(dictionaryMap);
        // Let's test the speed of the two Aho-Corasick automata
        System.out.printf("Parsing document which contains %d characters, with a dictionary of %d words.\n", text.length(), dictionary.size());
        long start = System.currentTimeMillis();
        ahoCorasickNaive.parseText(text);
        long costTimeNaive = System.currentTimeMillis() - start;
        start = System.currentTimeMillis();
        ahoCorasickDoubleArrayTrie.parseText(text, new AhoCorasickDoubleArrayTrie.IHit<String>()
        {
            @Override
            public void hit(int begin, int end, String value)
            {

            }
        });
        long costTimeACDAT = System.currentTimeMillis() - start;
        System.out.printf("%-15s\t%-15s\t%-15s\n", "", "Naive", "ACDAT");
        System.out.printf("%-15s\t%-15d\t%-15d\n", "time", costTimeNaive, costTimeACDAT);
        System.out.printf("%-15s\t%-15.2f\t%-15.2f\n", "char/s", (text.length() / (costTimeNaive / 1000.0)), (text.length() / (costTimeACDAT / 1000.0)));
        System.out.printf("%-15s\t%-15.2f\t%-15.2f\n", "rate", 1.0, costTimeNaive / (double) costTimeACDAT);
        System.out.println("===========================================================================");
    }

    /**
     * Compare my AhoCorasickDoubleArrayTrie with robert-bor's aho-corasick, notice that robert-bor's aho-corasick is
     * compiled under jdk1.8, so you will need jdk1.8 to run this test<br>
     * To avoid JVM wasting time on allocating memory, please use -Xms512m -Xmx512m -Xmn256m .
     * @throws Exception
     */
    public void testBenchmark() throws Exception
    {
        runTest("en/dictionary.txt", "en/text.txt");
        runTest("cn/dictionary.txt", "cn/text.txt");
    }

    public void testSaveAndLoad() throws Exception
    {
        AhoCorasickDoubleArrayTrie<String> acdat = buildASimpleAhoCorasickDoubleArrayTrie();
        final String tmpPath = System.getProperty("java.io.tmpdir").replace("\\\\", "/") + "/acdat.tmp";
        System.out.println("Saving acdat to: " + tmpPath);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(tmpPath));
        out.writeObject(acdat);
        out.close();
        System.out.println("Loading acdat from: " + tmpPath);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(tmpPath));
        acdat = (AhoCorasickDoubleArrayTrie<String>) in.readObject();
        validateASimpleAhoCorasickDoubleArrayTrie(acdat);
    }
}

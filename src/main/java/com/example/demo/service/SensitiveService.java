package com.example.demo.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class SensitiveService implements InitializingBean{
    private static final Logger logger=LoggerFactory.getLogger(SensitiveService.class);
    private static final String DEFAULT_REPLACEMENT="***";

    @Override
    public void afterPropertiesSet() throws Exception {
        rootNode=new TrieNode();
        try{
            InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("sensitive.txt");
            InputStreamReader reader=new InputStreamReader(is);
            BufferedReader bufferedReader=new BufferedReader(reader);
            String lineTxt;
            while((lineTxt=bufferedReader.readLine())!=null){
                lineTxt=lineTxt.trim();
                addText(lineTxt);
            }
            reader.close();
        }catch (Exception e){
            logger.error("error","敏感词文件提取失败");
        }
    }


    private class TrieNode{
        //判断是否为结束的标志
        private boolean end=false;
        private Map<Character,TrieNode> map=new HashMap<>();
        //向指定位置添加节点树
        void addSubNode(Character c,TrieNode trieNode){
            map.put(c,trieNode);
        }
        //判断是否为结束节点
        boolean isKeywordEnd(){return end;}
        //获取下一个节点
        TrieNode getSubNode(Character c){
            return map.get(c);
        }
        //设节点的结束标志
        void setKeywordEnd(boolean end){
            this.end=end;
        }
        public int getSubNodeCount(){return map.size();}
    }
    private TrieNode rootNode=new TrieNode();
    //判断是否为东亚文字
       public boolean isAsia(Character c){
             int a=(int)c;

                 return !CharUtils.isAsciiAlphanumeric(c)&&(a<0x2E80||a>0x9FFF);


    }


    //将一行文本添加进前缀树
       private void addText(String text){
        TrieNode tempNode=rootNode;

        for(int i=0;i<text.length();i++){
            Character c=text.charAt(i);
            if(isAsia(c)){
                continue;
            }
            TrieNode SubNode=tempNode.getSubNode(c);
            //未初始化
            if(SubNode==null){
                SubNode=new TrieNode();
                tempNode.addSubNode(c,SubNode);

            }
            tempNode=SubNode;
            if(i==text.length()-1){
                tempNode.setKeywordEnd(true);
            }
        }


       }
       //敏感词过滤算法
    public String filter(String text){
           if(StringUtils.isBlank(text)){
               return text;
           }
           StringBuilder stringBuilder=new StringBuilder();
           String replace=DEFAULT_REPLACEMENT;
           TrieNode tempNode=rootNode;
           int begin=0;
           int position=0;
           while(begin<text.length()){
               Character c=text.charAt(position);
               if(isAsia(c)){
                   if(begin==position){
                       stringBuilder.append(c);
                       begin++;
                   }
                   position++;
                   continue;
               }
               if(tempNode.getSubNode(c)!=null){
                   tempNode=tempNode.getSubNode(c);
                   if(tempNode.isKeywordEnd()){
                       stringBuilder.append(replace);
                       begin=position+1;
                       position=begin;
                       tempNode=rootNode;
                   }
                   position++;
               }
               else{
                   stringBuilder.append(text.charAt(begin));
                   begin++;
                   position=begin;
                   tempNode=rootNode;
               }

           }
        stringBuilder.append(text.substring(begin));
           return stringBuilder.toString();

    }


    public static void main(String[] args) {
        SensitiveService s=new SensitiveService();
        s.addText("色情");
        s.addText("好色");
        System.out.println(s.filter("  色woai色 情"));

    }
}

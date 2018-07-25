 <%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <style>
 pre {font-size: 9pt;font-family: 맑은 고딕}
 </style>
 
  <div class="x_title">
    <h2>zTree</h2>
 	 <div class="clearfix"></div>
</div>
 
	 
<h5 class="page-header"><span class="glyphicon glyphicon-th-large"></span> 용어</h5>
		 
<pre>
 HTTP		CRUD	SQL					관계 DB				엘라스틱서치
-------------------------------------------		--------------------------------------------------------
GET		Read		Select				데이터베이스 (Database)		인덱스 (index)
PUT		Update	Update			테이블(Table)			타입(Type)
POST		Create	Insert				열(Row)				도큐먼트 (Document)
DELETE	Delete	Delete			행(Column)				필드(Field)
								스키마(Schema)			매핑(Mapping)
</pre>
	
	
	
	<h5 class="page-header"><span class="glyphicon glyphicon-th-large"></span> elasticsearch</h5>
<pre>
1. elasticsearch 설치
    1-1 다운로드
	https://www.elastic.co/downloads/elasticsearch

    1-2 실행
	~/bin/elasticsearch --cluster.name my_cluster_name --node.name my_node_name

    1-3 크롬플로그인 설치
	ElasticSearch Toolbox

    1-3 설명서 링크
	https://github.com/wikibook/elasticsearch

2. 플러그인 설치

   2-1 한글 형태소 분석기(은전한입)
	https://bitbucket.org/eunjeon/seunjeon/src/6e8a067fb9a12bcdcdd7f858fd84714c94835f04/elasticsearch/
	plugin install org.bitbucket.eunjeon/elasticsearch-analysis-seunjeon/2.3.2.0

  2-2  elasticsearch-HQ
	plugin install royrusso/elasticsearch-HQ
	http://domain:port/_plugin/hq/

3. index 만들기
	--------------------------------------------------------
	POST news
	{
	    "settings": {
	        "number_of_shards": 3,
	        "number_of_replicas": 0,
	        "analysis": {
	            "analyzer": {
	                "korean_analyzer": {
	                    "type": "custom",
	                    "tokenizer": "seunjeon_default_tokenizer",
	                    "char_filter": [
	                  		"html_strip"
	               		]
	                }
	            },
	            "tokenizer": {
	                "seunjeon_default_tokenizer": {
	                    "type": "seunjeon_tokenizer"
	                }
	            }
	        }
	    }
	}
	--------------------------------------------------------	
	
4. Mapping - type 생성 및 맵핑
	--------------------------------------------------------
	 POST news/stock
	 {
	     "properties": {
	         "seq": { "type": "integer", "index": "not_analyzed" },
	         "title": { "type": "string", "analyzer": "korean_analyzer"},
	         "description": { "type": "string", "analyzer": "korean_analyzer"},
	         "regdate": { "type": "date"}
	     }
	 }
	--------------------------------------------------------		
	
5. 검색 Query
	--------------------------------------------------------	
	 GET news/stock/_search
	 {
	    "query": {
	        "query_string": {
	            "fields": ["title","description"],
	            "query": "박찬호 한화",
	            "default_operator": "AND"
	        }
	    },
	    
	     "filter" : {
	     	"range": {"regdate": {"gte": "2012-04-01","lte": "2016-01-01"}}
	    },
	    
	    "_source": {
	        "include": ["title", "description","reg_date"]
	    },
	
	   "highlight": {               
	        "fields": [
	            {"title": {"fragment_size" : 100, "number_of_fragments" : 3}},
	            {"description": {"fragment_size" : 200, "number_of_fragments" : 3}}
	        ]
	    },
	
	    "sort": [
	    	{"_score": "asc"},
	    	{"regdate": "desc"}
	    ],
	    "from": 0,
	    "size": 10
	}	
	--------------------------------------------------------	
		
5. Document 생성 - 크롬플로그인 (ElasticSearch Toolbox)
	POST taxholic/news/1
	{ "seq":1 ,"title":"제목이다","description":"설명이다",regdate : "2016-01-01T00:00:00+0900"}
	
6. 삭제
	DELETE taxholic/news/1
	
7. 결과 확인
	http://127.0.0.1:9200/taxholic/news/1?pretty
	http://127.0.0.1:9200/taxholic/news/_search?q=title:제목fields=tile,description&sort=regdate:desc&size=20&pretty	
</pre>



	<h5 class="page-header"><span class="glyphicon glyphicon-th-large"></span> logstash</h5>
<pre>
1. logstash 설치

    1-1 다운로드
	https://www.elastic.co/downloads/elasticsearch
	
    1-2 실행
	~/bin/logstash -f logstash.conf --configtest
	~/bin/logstash -f logstash.conf

    1-3 logstash jdbc 설정
	-  jdbc 커넥트 복사 ~lib/mysql-connector-java-5.1.38.jar
	- vi  logstash.conf
	
	--------------------------------------------------------
	input {
        jdbc {
	      type => "stock_create"
	      jdbc_driver_library => "lib/mysql-connector-java-5.1.38.jar"
	      jdbc_driver_class => "com.mysql.jdbc.Driver"
	      jdbc_connection_string => "jdbc:mysql://localhost:3306/stock?useSSL=false"
	      jdbc_user => "xxx"
	      jdbc_password => "xxx"
	      last_run_metadata_path => "C:/01.work/server/elasticsearch/logstash-2.3.4/create_logstash_jdbc_last_run"
	      statement => "SELECT seq,title,description,reg_date AS regdate FROM sk_news WHERE reg_date >=  DATE_ADD(:sql_last_value, INTERVAL '9' DAY_HOUR)"
	      schedule => "* * * * *"
        }

         jdbc {
	       type => "stock_delete"
	       jdbc_driver_library => "lib/mysql-connector-java-5.1.38.jar"
	       jdbc_driver_class => "com.mysql.jdbc.Driver"
	       jdbc_connection_string => "jdbc:mysql://localhost:3306/stock?useSSL=false"
	       jdbc_user => "xxx"
	       jdbc_password => "xxx"
	       last_run_metadata_path => "C:/01.work/server/elasticsearch/logstash-2.3.4/delete_logstash_jdbc_last_run"
	       statement => "SELECT sk_news_seq FROM sk_news_delete WHERE reg_date >=  DATE_ADD(:sql_last_value, INTERVAL '9' DAY_HOUR)"
	       schedule => "* * * * *"
        }
	}
 
	output {

		if [type] == "stock_create" {
			 elasticsearch { 
				#action => "delete"
				#action => "update"
				#action => "create"
				hosts => "localhost:9200"
				index => "taxholic"
				document_type => "news"
				document_id => "%{seq}"
			}
		}
	
		if [type] == "stock_delete" {
			 elasticsearch { 
				action => "delete"
				hosts => "localhost:9200"
				index => "taxholic"
				document_type => "news"
				document_id => "%{sk_news_seq}"
			}
		}
	
		stdout { codec => json_lines }
	}
	--------------------------------------------------------
</pre>


	<h5 class="page-header"><span class="glyphicon glyphicon-th-large"></span> kibana</h5>
<pre>
kibana기본 설정을 사용하면 localhost:9200 의 elasticsearch에 연결 되도록 되어있다.

설정파일: ./kibana-4.5.1-linux-x64/config/kibana.yml

필드:
   # The Elasticsearch instance to use for all your queries.
   # elasticsearch.url: "http://localhost:9200" 
      
Browser를 사용해 http://localhost:5601/ 에 연결하여 확인한다.


nginx.conf example
--------------------------------------------------------

input {
    file {
        type => "nginx"
        start_position => "beginning"
        path => [ "/var/log/nginx/*.log" ]
    }
}
filter {
    if [type] == "nginx" {
        # NGINX access log entry
        grok {
            match => [ "message" , "%{COMBINEDAPACHELOG}+%{GREEDYDATA:extra_fields}"]
            overwrite => [ "message" ]
        }
 
        mutate {
            convert => ["response", "integer"]
            convert => ["bytes", "integer"]
            convert => ["responsetime", "float"]
        }
 
        geoip {
            source => "clientip"
            target => "geoip"
            add_tag => [ "nginx-geoip" ]
        }
 
        date {
            match => [ "timestamp" , "dd/MMM/YYYY:HH:mm:ss Z" ]
            remove_field => [ "timestamp" ]
        }.,
 
        useragent {
            source => "agent"
        }
 
        # NGINX error log
        grok {
            match => [ "message" , "(?<timestamp>%{YEAR}[./-]%{MONTHNUM}[./-]%{MONTHDAY}[- ]%{TIME}) \[%{LOGLEVEL:severity}\] %{POSINT:pid}#%{NUMBER}: %{GREEDYDATA:errormessage}(?:, client: (?<client>%{IP}|%{HOSTNAME}))(?:, server: %{IPORHOST:server})(?:, request: %{QS:request})?(?:, upstream: \"%{URI:upstream}\")?(?:, host: %{QS:host})?(?:, referrer: \"%{URI:referrer}\")"]
            overwrite => [ "message" ]
        }
 
        geoip {
            source => "client"
            target => "geoip"
            add_tag => [ "nginx-geoip" ]
        }
 
        date {
            match => [ "timestamp" , "YYYY/MM/dd HH:mm:ss" ]
            remove_field => [ "timestamp" ]
        }
    }
}
 
output {
    stdout { }
    elasticsearch { hosts =>  ["localhost:9200"] }
}

--------------------------------------------------------

</pre>


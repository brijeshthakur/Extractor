# Extractor

Extractor Library extracts Mentions (@abc) Links (https://www.example.com) and Emoticons (smile) from a given String and returns JSON in below format: 

Input : @bob (success) link https://www.example.com

Output: 

{
   "mentions":[
      "bob"
   ],
   "emoticons":[
      "success"
   ],
   "links":[
      {
         "url":"https:\/\/www.example.com",
         "title":"Example Domain"
      }
   ]
}


It also returns Error which it encounter during extract process. 

Output with error : 


{
   "mentions":[
      "bob"
   ],
   "emoticons":[
      "success"
   ],
   "links":[
      {
         "url":"https:\/\/www.example.com"
      }
   ],
   "errors":[
      {
         "errorType":"NO_NETWORK",
         "error":"Not connected to internet"
      }
   ]
}




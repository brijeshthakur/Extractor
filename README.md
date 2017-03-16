# Extractor

Extractor Library (https://github.com/brijeshthakur/Extractor/tree/master/extractor) extracts Mentions (@abc) Links (https://www.example.com) and Emoticons (smile) from a given String and returns JSON.

Library follows Open Close Design principal. So in future if this library need to support more features to extract from given string, can be accommodated easily and from outside the world the interface for communication remains same.

As of now, for creating JSON output, I am using the Android’s default JSON (org.json) 
package, but we can switch to GSON if we have to deal with heavy loaded JSON.

I’ve added an Sample App for demo purpose which included “extractor” as a module dependency. 

Note : Sample App is very basic and just for demo to show how can one use it in actual App. 

### Example

```
Input : @bob (success) link https://www.example.com
```

```
Output:
```

```Json 
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
```

* It also returns error which it encounter during extract process. 

```
Output with error:
``` 
```Json
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
```

 


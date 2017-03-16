# Extractor

[Extractor] (https://github.com/brijeshthakur/Extractor/tree/master/extractor) library extracts Mentions (@abc) Links (https://www.example.com) and Emoticons (smile) from a given string and returns JSON as a response.

Library follows abstraction and extensibility design principle, so that in future if this library needs to support more features to extract from a given string, It can be accommodated easily and from outside the world the interface for communication remains same. It propagates error to the caller as part of JSON response if it fails to extract any of the feature. Extraction part decoupled in a way that, If one of the extraction gets failed doesn’t impact another one and return the result json at least for those which got extracted successfully.

For every new extract request, Caller will have to create a new ExtractRequest(). I followed this to avoid synchronization and performance issues.

Extract request call follows callback mechanism, wherein the caller will receive the callback once library completes the extraction of links, emoticons, mentions.

Extraction process happens in background so that UI thread will be always free.  

As of now, for creating JSON output, I am using the Android’s default JSON *(org.json)* package, but we can switch to GSON if we have to deal with heavy loaded JSON.

I’ve added an Sample App for demo purpose which included “extractor” as a module dependency. 

**Note** : Sample App is very basic and just for demo to show how can one use it in actual App. 

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

 


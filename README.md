# messages-extractor

A handy tool for extracting Messages on macOS if you setup sync with iOS.

[Set up iPhone to get SMS texts on Mac](https://support.apple.com/en-gb/guide/messages/icht8a28bb9a/mac)

Examples:
```
String dbLocation = System.getProperty("user.home") + "/Library/Messages/chat.db";
extractor = new MessagesExtractor(dbLocation);

Map<String, List<Message>> allChats = extractor.getMessages();

Map<String, List<Message>> lastThreeDays = extractor.getMessages(LocalDate.now().minusDays(3).atStartOfDay());

Map<String, List<Message>> selectedChatsLastYear = extractor.getMessages(Arrays.asList("Google", "Apple"),
                LocalDate.now().minusYears(1).atStartOfDay());
                
Map<String, List<Message>> selectedChats = extractor.getMessages(Arrays.asList("Google", "Apple"));              
```

Note, by default you cannot access `~/Library/Messages/chat.db`. You may copy it to `~/Messages/chat.db` with [mycopier](https://github.com/pavel-alay/mycopier) or grant full access to `java`.

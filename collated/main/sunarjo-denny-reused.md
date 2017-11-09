# sunarjo-denny-reused
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
    private Consumer<String> setOrder(Prefix prefix) {
        return s-> {
            attribute = prefix.toString();

            if (s.equals(SortCommand.REVERSE_SEQUENCE)) {
                isReversed = new Boolean(true);
                return;
            } else {
                isReversed = new Boolean(false);
                return;
            }
        };
    }
}
```

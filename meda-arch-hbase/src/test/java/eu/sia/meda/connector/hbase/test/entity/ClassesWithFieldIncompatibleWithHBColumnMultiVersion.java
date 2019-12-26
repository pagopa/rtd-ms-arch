package eu.sia.meda.connector.hbase.test.entity;

import java.util.Map;
import java.util.NavigableMap;

import eu.sia.meda.connector.hbase.annotation.Family;
import eu.sia.meda.connector.hbase.annotation.HBColumnMultiVersion;
import eu.sia.meda.connector.hbase.annotation.HBRecord;
import eu.sia.meda.connector.hbase.annotation.HBRowKey;
import eu.sia.meda.connector.hbase.annotation.HBTable;

public class ClassesWithFieldIncompatibleWithHBColumnMultiVersion {
    @SuppressWarnings("unused")
    @HBTable(name = "blah", families = {@Family(name = "f")})
    public static class NotMap implements HBRecord<String> {
        @HBRowKey
        protected String key = "key";

        @Override
        public String composeRowKey() {
            return key;
        }

        @Override
        public void parseRowKey(String rowKey) {
            this.key = rowKey;
        }

        @HBColumnMultiVersion(family = "f", column = "c")
        private Integer i;
    }

    @SuppressWarnings("unused")
    @HBTable(name = "blah", families = {@Family(name = "f")})
    public static class NotNavigableMap implements HBRecord<String> {
        @HBRowKey
        protected String key = "key";

        @Override
        public String composeRowKey() {
            return key;
        }

        @Override
        public void parseRowKey(String rowKey) {
            this.key = rowKey;
        }

        @HBColumnMultiVersion(family = "f", column = "c")
        private Map<Long, Integer> i;
    }

    @SuppressWarnings("unused")
    @HBTable(name = "blah", families = {@Family(name = "f")})
    public static class EntryKeyNotLong implements HBRecord<String> {
        @HBRowKey
        protected String key = "key";

        @Override
        public String composeRowKey() {
            return key;
        }

        @Override
        public void parseRowKey(String rowKey) {
            this.key = rowKey;
        }

        @HBColumnMultiVersion(family = "f", column = "c")
        private NavigableMap<Integer, Integer> i;
    }
}

package eu.sia.meda.connector.hbase.test.entity;

import eu.sia.meda.connector.hbase.annotation.Family;
import eu.sia.meda.connector.hbase.annotation.HBColumn;
import eu.sia.meda.connector.hbase.annotation.HBRecord;
import eu.sia.meda.connector.hbase.annotation.HBRowKey;
import eu.sia.meda.connector.hbase.annotation.HBTable;

public class ClassesWithInvalidHBTableAnnotation {

    @SuppressWarnings("unused")
    @HBTable(name = "blah", families = {@Family(name = "f", versions = 0)})
    public static class InvalidVersions implements HBRecord<String> {
        @HBRowKey
        private String key = "key";

        @Override
        public String composeRowKey() {
            return key;
        }

        @Override
        public void parseRowKey(String rowKey) {
            this.key = rowKey;
        }

        @HBColumn(family = "f", column = "c")
        private Integer i;
    }

    @HBTable(name = "", families = {@Family(name = "f", versions = 5)})
    public static class EmptyTableName implements HBRecord<String> {
        @HBRowKey
        private String key = "key";

        @Override
        public String composeRowKey() {
            return key;
        }

        @Override
        public void parseRowKey(String rowKey) {
            this.key = rowKey;
        }

        @HBColumn(family = "f", column = "c")
        private Integer i;
    }

    @HBTable(name = "blah", families = {@Family(name = "", versions = 3)})
    public static class EmptyColumnFamily implements HBRecord<String> {
        @HBRowKey
        private String key = "key";

        @Override
        public String composeRowKey() {
            return key;
        }

        @Override
        public void parseRowKey(String rowKey) {
            this.key = rowKey;
        }

        @HBColumn(family = "f", column = "c")
        private Integer i;
    }

    @HBTable(name = "blah", families = {@Family(name = "f", versions = 2), @Family(name = "f", versions = 3)})
    public static class DuplicateColumnFamilies implements HBRecord<String> {
        @HBRowKey
        private String key = "key";

        @Override
        public String composeRowKey() {
            return key;
        }

        @Override
        public void parseRowKey(String rowKey) {
            this.key = rowKey;
        }

        @HBColumn(family = "f", column = "c")
        private Integer i;
    }

    public static class MissingHBTableAnnotation implements HBRecord<String> {
        @HBRowKey
        private String key = "key";

        @Override
        public String composeRowKey() {
            return key;
        }

        @Override
        public void parseRowKey(String rowKey) {
            this.key = rowKey;
        }

        @HBColumn(family = "f", column = "c")
        private Integer i;
    }
}

package eu.sia.meda.connector.hbase.test.entity;

import eu.sia.meda.connector.hbase.annotation.Family;
import eu.sia.meda.connector.hbase.annotation.Flag;
import eu.sia.meda.connector.hbase.annotation.HBColumn;
import eu.sia.meda.connector.hbase.annotation.HBRecord;
import eu.sia.meda.connector.hbase.annotation.HBRowKey;
import eu.sia.meda.connector.hbase.annotation.HBTable;
import eu.sia.meda.connector.hbase.codec.BestSuitCodec;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("unused")
@ToString
@EqualsAndHashCode
@HBTable(name = "students", families = {@Family(name = "a")}, rowKeyCodecFlags = {@Flag(name = BestSuitCodec.SERIALIZE_AS_STRING, value = "true")})
public class Student implements HBRecord<Integer> {
    @HBRowKey
    private Integer studentId;

    @HBColumn(family = "a", column = "name")
    private String name;

    @Override
    public Integer composeRowKey() {
        return studentId;
    }

    @Override
    public void parseRowKey(Integer rowKey) {
        studentId = rowKey;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public Student() {

    }

    public Student(Integer studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }
}

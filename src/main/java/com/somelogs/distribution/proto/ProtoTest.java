package com.somelogs.distribution.proto;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 描述
 *
 * @author LBG - 2019/12/31
 */
public class ProtoTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        PersonProto.Person.Builder builder = PersonProto.Person.newBuilder();
        builder.setId(2);
        builder.setName("tom");
        PersonProto.Person person = builder.build();
        byte[] bytes = person.toByteArray();

        PersonProto.Person parsePerson = PersonProto.Person.parseFrom(bytes);
        System.out.println(parsePerson.toString());
    }
}

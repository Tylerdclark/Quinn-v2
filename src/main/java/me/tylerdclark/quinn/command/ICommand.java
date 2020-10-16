package me.tylerdclark.quinn.command;

import java.util.List;

public interface Commandable {
    void handle();
    String getName();
    default List<String> getAliases(){
        return List.of();
    };
}

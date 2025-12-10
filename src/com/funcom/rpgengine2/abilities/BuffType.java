package com.funcom.rpgengine2.abilities;

public enum BuffType {
    BUFF(0),
    DEBUFF(1),
    NEUTRAL(2);

    private final byte id;
    private static final BuffType[] ALL;

    static {
        ALL = values();
    }

    BuffType(int id) {
        if (id > 127 || id < -128) {
            throw new IllegalArgumentException("id value out of bounds, refactor id to use a larger primitive type");
        }
        this.id = (byte) id;
    }

    public byte getId() {
        return this.id;
    }

    public static BuffType valueById(byte id) {
        for (BuffType buffType : ALL) {
            if (buffType.getId() == id) {
                return buffType;
            }
        }
        throw new IllegalArgumentException("unknown BuffType: id=" + id);
    }
}
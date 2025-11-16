package com.chess.engine.enums;

/**
 * Перечисление типов шахматных фигур
 */
public enum ID {
    /** Король */
    KING {
        @Override
        public String toString() {
            return "K";
        }
        public String toFullString() {
            return "King";
        }
    },
    /** Ферзь */
    QUEEN {
        @Override
        public String toString() {
            return "Q";
        }
        public String toFullString() {
            return "Queen";
        }
    },
    /** Ладья */
    ROOK {
        @Override
        public String toString() {
            return "R";
        }
        public String toFullString() {
            return "Rook";
        }
    },
    /** Слон */
    BISHOP {
        @Override
        public String toString() {
            return "B";
        }
        public String toFullString() {
            return "Bishop";
        }
    },
    /** Конь */
    KNIGHT {
        @Override
        public String toString() {
            return "N";
        }
        public String toFullString() {
            return "Knight";
        }
    },
    /** Пешка */
    PAWN {
        @Override
        public String toString() {
            return "";
        }
        public String toFullString() {
            return "Pawn";
        }
    },

    /** Визирь (специальная фигура) */
    VIZAR {
        @Override
        public String toString() {
            return "V";
        }
        public String toFullString() {
            return "Vizar";
        }
    },

    /** Боевая машина (специальная фигура) */
    WARCAR {
        @Override
        public String toString() {
            return "W";
        }
        public String toFullString() {
            return "WarCar";
        }
    },

    /** Жираф (специальная фигура) */
    GIRAFFE {
        @Override
        public String toString() { return "G"; }
        public String toFullString() { return "Giraffe"; }
    };

    /**
     * Возвращает полное строковое представление типа фигуры
     * @return полное имя типа фигуры
     */
    public abstract String toFullString();

}
package com.charles.jdk8.stream;

class Task {

    public enum Status {
        OPEN(1, 2, 3),
        CLOSED(4, 5, 6);

        private final int[] value;

        Status(int... value) {
            this.value = value;
        }

        public int[] getValue() {
            return value;
        }
    }

    private final Status status;
    private final Integer points;

    Task(final Status status, final Integer points) {
        this.status = status;
        this.points = points;
    }

    public Integer getPoints() {
        return points;
    }

    public Status getStatus() {
        return status;
    }

    public int[] getStatusValue() {
        return status.getValue();
    }

    @Override
    public String toString() {
        return String.format("[%s, %d]", status, points);
    }
}
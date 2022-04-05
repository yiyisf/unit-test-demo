package com.paic.unittest.demo.service.dto;

public class XserviceDto {
    private Long id;
    private String field;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XserviceDto that = (XserviceDto) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "XserviceDto{" +
                "id=" + id +
                ", field='" + field + '\'' +
                '}';
    }
}

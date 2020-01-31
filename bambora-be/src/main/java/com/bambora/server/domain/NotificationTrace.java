package com.bambora.server.domain;

import com.bambora.server.commons.Method;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "notification_trace")
public class NotificationTrace implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "method")
    private Method method;

    @Column(name = "signature", length = 2048)
    private String signature;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "data", length = 2048)
    private String data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTrace that = (NotificationTrace) o;
        return Objects.equals(id, that.id) &&
                method == that.method &&
                Objects.equals(signature, that.signature) &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, method, signature, uuid, data);
    }

    @Override
    public String toString() {
        return "NotificationTrace{" +
                "id=" + id +
                ", method=" + method +
                ", signature='" + signature + '\'' +
                ", uuid='" + uuid + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}

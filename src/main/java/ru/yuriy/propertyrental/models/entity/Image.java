package ru.yuriy.propertyrental.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
public class Image
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image", nullable = false)
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "content_type", length = 50)
    private String contentType;

    @Column(name = "size")
    private Long size;

    @Column(name = "preview_image")
    private Boolean previewImage = false;

    @Column(name = "image_bytes", columnDefinition = "bytea")
    private byte[] imageBytes;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apartment")
    private Apartment apartment;

    @PrePersist
    private void setSize()
    {
        if (imageBytes == null)
            throw new IllegalStateException("Поле imageBytes является null!");
        this.size = (long) imageBytes.length;
    }
}
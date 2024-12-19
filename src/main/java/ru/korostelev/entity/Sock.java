package ru.korostelev.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "catalogue", name = "t_sock")
public class Sock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_color")
    private String color;

    @Column(name = "c_percentageCotton")
    private Integer percentageCotton;

    @Column(name = "c_pieces")
    private Integer pieces;

    public Sock(String color, Integer percentageCotton, Integer pieces) {
        this.color = color;
        this.percentageCotton = percentageCotton;
        this.pieces = pieces;
    }
}

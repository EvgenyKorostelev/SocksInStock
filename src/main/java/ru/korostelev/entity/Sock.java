package ru.korostelev.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "catalogue", name = "t_socks")
public class Sock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_color")
    private String color;

    @Column(name = "c_percentage_cotton")
    private Integer percentageCotton;

    @Column(name = "c_pieces")
    private Integer pieces;

}

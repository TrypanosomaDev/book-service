package com.example.bookservice.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="Order")
@Table(name="order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="customerId", nullable=false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PREPARING;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<BookOrder> books = new HashSet<>();

    public void addBook(Book book, Integer number) {
        BookOrder bookOrder = new BookOrder(book, this);
        bookOrder.setNumber(number);
        books.add(bookOrder);
        book.getOrders().add(bookOrder);
    }

    public void removeBook(Book book) {
        for (Iterator<BookOrder> iterator = books.iterator();
             iterator.hasNext(); ) {
            BookOrder bookOrder = iterator.next();

            if (bookOrder.getOrder().equals(this) &&
                    bookOrder.getBook().equals(book)) {
                iterator.remove();
                bookOrder.getBook().getOrders().remove(bookOrder);
                bookOrder.setOrder(null);
                bookOrder.setBook(null);
            }
        }
    }
}

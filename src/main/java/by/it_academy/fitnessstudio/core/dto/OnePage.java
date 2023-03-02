package by.it_academy.fitnessstudio.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class OnePage<T> {//TODO:билдер
    @JsonProperty(index = 1)
    private Integer number;
    @JsonProperty(index = 2)
    private Integer size;
    @JsonProperty(value = "total_pages", index = 3)
    private Integer totalPages;
    @JsonProperty(value = "total_elements", index = 4)
    private Long totalElements;
    @JsonProperty(index = 5)
    private boolean first;
    @JsonProperty(value = "number_of_elements", index = 6)
    private Integer numberOfElements;
    private boolean last;
    private List<T> content;

    public OnePage(Integer number, Integer size,
                   Integer totalPages, Long totalElements,
                   boolean first, Integer numberOfElements,
                   boolean last, List<T> content) {
        this.number = number;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.first = first;
        this.numberOfElements = numberOfElements;
        this.last = last;
        this.content = content;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnePage<?> onePage = (OnePage<?>) o;
        return first == onePage.first
                && last == onePage.last
                && Objects.equals(number, onePage.number)
                && Objects.equals(size, onePage.size)
                && Objects.equals(totalPages, onePage.totalPages)
                && Objects.equals(totalElements, onePage.totalElements)
                && Objects.equals(numberOfElements, onePage.numberOfElements)
                && Objects.equals(content, onePage.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, size, totalPages, totalElements, first, numberOfElements, last, content);
    }

    public static class OnePageBuilder<T> {
        private Integer number;
        private Integer size;
        private Integer totalPages;//TODO: в json total_pages
        private Long totalElements;//TODO: в json total_elements
        private boolean first;
        private Integer numberOfElements;
        private boolean last;
        private List<T> content;

        private OnePageBuilder() {
        }

        public static <T> OnePageBuilder<T> create() {
            return new OnePageBuilder<>();
        }

        public OnePageBuilder<T> setNumber(Integer number) {
            this.number = number;
            return this;
        }

        public OnePageBuilder<T> setSize(Integer size) {
            this.size = size;
            return this;
        }

        public OnePageBuilder<T> setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public OnePageBuilder<T> setTotalElements(Long totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public OnePageBuilder<T> setFirst(boolean first) {
            this.first = first;
            return this;
        }

        public OnePageBuilder<T> setNumberOfElements(Integer numberOfElements) {
            this.numberOfElements = numberOfElements;
            return this;
        }

        public OnePageBuilder<T> setLast(boolean last) {
            this.last = last;
            return this;
        }

        public OnePageBuilder<T> setContent(List<T> content) {
            this.content = content;
            return this;
        }

        public OnePage<T> build(){
            return new OnePage<>(number, size, totalPages, totalElements, first, numberOfElements, last, content);
        }
    }
}

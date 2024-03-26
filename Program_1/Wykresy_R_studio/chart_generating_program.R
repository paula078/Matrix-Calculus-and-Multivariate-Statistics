library(ggplot2)
lines <- readLines("operationsCount.txt")

matrix <- list()
labels <- list()

for (line in lines) {
  # Jeśli linia zaczyna się od 'l' to traktujemy ją jako etykietę
  if (startsWith(line, "l")) {
    labels <- c(labels, as.integer(sub("l=", "", line)))
  } else {
    matrix <- c(matrix, list(as.integer(strsplit(line, " ")[[1]])))
  }
}

# Inicjalizacja pustej ramki danych
all_data <- data.frame()

# Przetwarzanie danych
for (i in 2:length(matrix)) {
  x <- matrix[[1]]
  y <- matrix[[i]]
  label <- paste("l=", labels[[i - 1]], sep="")
  
  data <- data.frame(x, y, label)
  
  # Dodanie danych do ogólnej ramki danych
  all_data <- rbind(all_data, data)
}

# Tworzenie wykresu
p <- ggplot(all_data, aes(x = x, y = y, color = label)) +
  geom_line(size = 0.8) +
  labs(x = "Rozmiar macierzy", y = "Ilość operacji flop", 
       title = "Zależność ilości flop od rozmiaru macierzy", color = "Legenda") +
  theme(plot.title = element_text(hjust = 0.5,  size = 10, face = "bold")) + 
  theme(legend.position = "right")  


print(p)
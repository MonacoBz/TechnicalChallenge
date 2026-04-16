import os
import random

# Configuración
folder_name = "src/main/resources/archivos"
num_files = 10
min_words = 500
max_words = 1000

# Texto base para generar contenido
lorem_text = (
    "lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor "
    "incididunt ut labore et dolore magna aliqua ut enim ad minim veniam quis nostrud "
    "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat "
)

def generate_files():
    # Crear la carpeta si no existe
    if not os.path.exists(folder_name):
        os.makedirs(folder_name)
        print(f"Carpeta creada: {folder_name}")

    words_list = lorem_text.split()

    for i in range(1, num_files + 1):
        file_name = f"archivo_{i}.txt"
        file_path = os.path.join(folder_name, file_name)
        
        # Determinar cantidad de palabras aleatoria
        target_word_count = random.randint(min_words, max_words)
        
        # Generar contenido
        content = " ".join(random.choices(words_list, k=target_word_count))
        
        # Escribir el archivo
        with open(file_path, "w", encoding="utf-8") as f:
            f.write(content)
        
        print(f"Generado: {file_name} ({target_word_count} palabras)")

if __name__ == "__main__":
    generate_files()
import os
import random

# Configuración
folder_name = "src/main/resources/archivos"
num_files = 10

# Diccionarios temáticos en inglés
themes = {
    "tech": [
        "software", "architecture", "microservices", "spring", "backend", "database", "injection",
        "controller", "security", "deployment", "container", "scalability", "latency", "request",
        "response", "middleware", "annotation", "configuration", "performance", "optimization"
    ],
    "nature": [
        "ecosystem", "biodiversity", "environment", "climate", "forest", "ocean", "wildlife",
        "conservation", "species", "habitat", "renewable", "atmosphere", "mountain", "river",
        "glacier", "evolution", "photosynthesis", "ecology", "sustainability", "landscape"
    ],
    "space": [
        "galaxy", "nebula", "astronomy", "star", "planet", "orbit", "telescope", "universe",
        "gravity", "black hole", "supernova", "exploration", "spacecraft", "satellite",
        "cosmos", "asteroid", "comet", "astrophysics", "lightyear", "constellation"
    ],
    "lifestyle": [
        "routine", "health", "mindfulness", "travel", "culture", "cooking", "exercise",
        "productivity", "community", "experience", "discovery", "leisure", "balance",
        "photography", "adventure", "journaling", "motivation", "hobbies", "wellness", "skills"
    ]
}

common_words = ["the", "and", "a", "is", "it", "in", "of", "to", "for", "with", "on", "that", "this", "by"]

def create_paragraph(theme_words, length=80):
    words = []
    for _ in range(length):
        # 40% palabras temáticas, 60% palabras comunes o random
        if random.random() > 0.6:
            words.append(random.choice(theme_words))
        else:
            words.append(random.choice(common_words))
    return " ".join(words).capitalize() + "."

def generate_files():
    if not os.path.exists(folder_name):
        os.makedirs(folder_name)

    theme_keys = list(themes.keys())

    for i in range(1, num_files + 1):
        file_name = f"documento_en_{i}.txt"
        file_path = os.path.join(folder_name, file_name)
        
        # Elegir un tema aleatorio para este archivo
        current_theme = random.choice(theme_keys)
        target_word_count = random.randint(500, 1000)
        
        content = []
        current_count = 0
        while current_count < target_word_count:
            para_len = random.randint(50, 100)
            paragraph = create_paragraph(themes[current_theme], para_len)
            content.append(paragraph)
            current_count += para_len
        
        with open(file_path, "w", encoding="utf-8") as f:
            f.write("\n\n".join(content))
        
        print(f"Creado: {file_name} | Tema: {current_theme} | Palabras: ~{current_count}")

if __name__ == "__main__":
    generate_files()

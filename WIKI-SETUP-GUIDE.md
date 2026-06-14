# Guía Completa: Wiki desde README con GitHub Actions

## Índice

1. [Resumen del Proyecto](#1-resumen-del-proyecto)
2. [Configuración del Proyecto Android](#2-configuración-del-proyecto-android)
3. [Documentación KDoc con Dokka](#3-documentación-kdoc-con-dokka)
4. [Configuración de GitHub Actions](#4-configuración-de-github-actions)
5. [Configuración de GitHub Pages](#5-configuración-de-github-pages)
6. [Configuración de la Wiki](#6-configuración-de-la-wiki)
7. [Estructura de Archivos](#7-estructura-de-archivos)
8. [Solución de Problemas](#8-solución-de-problemas)

---

## 1. Resumen del Proyecto

Este proyecto demuestra cómo:
- Documentar código Android con **KDoc**
- Generar documentación HTML con **Dokka**
- Desplegar documentación en **GitHub Pages**
- Sincronizar README con **GitHub Wiki** automáticamente

### Tecnologías Utilizadas

| Tecnología | Propósito |
|------------|-----------|
| Kotlin | Lenguaje de programación |
| Dokka | Generación de documentación |
| GitHub Actions | CI/CD automatizado |
| GitHub Pages | Hosting de documentación |
| GitHub Wiki | Documentación colaborativa |

---

## 2. Configuración del Proyecto Android

### 2.1. Archivo `build.gradle.kts` (Raíz)

```kotlin
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.jetbrains.dokka) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
}
```

### 2.2. Archivo `app/build.gradle.kts`

#### Plugins

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.jetbrains.dokka)  // ← Plugin de Dokka
    alias(libs.plugins.firebase.crashlytics)
}
```

#### Configuración de Dokka

```kotlin
// Crea la documentación en una carpeta fuera de /app llamada /docs
tasks.dokkaHtml.configure {
    outputDirectory.set(file("../docs"))
}
```

#### Dependencias de Dokka

```kotlin
dependencies {
    // ... otras dependencias
    
    // Dokka
    dokkaPlugin(libs.dokka.documentation)
    dokkaPlugin(libs.dokka.mathjax)
    dokkaHtmlPlugin(libs.dokka.kotlin)
    dokkaHtmlPartialPlugin(libs.dokka.kotlin)
}
```

### 2.3. Archivo `libs.versions.toml` (Gradle Version Catalog)

Asegúrate de tener las versiones de Dokka:

```toml
[versions]
dokka = "1.9.20"

[libraries]
dokka-documentation = { group = "org.jetbrains.dokka", name = "dokka-core", version.ref = "dokka" }
dokka-mathjax = { group = "org.jetbrains.dokka", name = "dokka-mathjax-plugin", version.ref = "dokka" }
dokka-kotlin = { group = "org.jetbrains.dokka", name = "dokka-kotlin-as-java", version.ref = "dokka" }

[plugins]
jetbrains-dokka = { id = "org.jetbrains.dokka", version.ref = "dokka" }
```

---

## 3. Documentación KDoc con Dokka

### 3.1. Sintaxis KDoc

#### Documentación de Clase

```kotlin
/**
 * Descripción de la clase y su propósito.
 *
 * @property nombrePropiedad Descripción de la propiedad
 * @author Autor
 * @version Versión
 * @see OtraClase
 */
class MiClase {
    // ...
}
```

#### Documentación de Función

```kotlin
/**
 * Descripción de qué hace la función.
 *
 * @param parametro1 Descripción del parámetro
 * @return Descripción del valor retornado
 * @throws ExcepcionCuando Cuando se lanza esta excepción
 */
fun miFuncion(parametro1: String): Int {
    // ...
}
```

### 3.2. Ejemplo: `MainActivity.kt`

```kotlin
/**
 * Actividad principal que muestra dos textos y un botón que crea un
 * [Toast](https://developer.android.com/reference/android/widget/Toast?hl=en).
 *
 * Esta actividad demuestra conceptos básicos de desarrollo Android:
 * <ol>
 *     <li>Añadir dependencias al proyecto</li>
 *     <li>Crear un layout en XML</li>
 *     <li>Sincronizar el proyecto con Firebase</li>
 * </ol>
 *
 * Utiliza [View Binding](https://developer.android.com/topic/libraries/view-binding) 
 * para acceder a las vistas del layout.
 *
 * @author Lourdes Rodríguez Morón
 * @version 1.0
 * @see AppCompatActivity
 * @see ActivityMainBinding
 */
class MainActivity : AppCompatActivity() {

    /**
     * Etiqueta para los logs de depuración.
     */
    private val TAG = "MainActivity"

    /**
     * Mensaje que se muestra en la interfaz.
     * Se inicializa de forma tardía con [lateinit].
     */
    lateinit var message: String

    /**
     * Referencia al binding de la actividad.
     * Permite acceder a las vistas del layout de forma segura.
     *
     * @see ActivityMainBinding
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * Se llama cuando se crea la actividad por primera vez.
     *
     * @param savedInstanceState Si la actividad se está recreando después de un cierre
     *                           previo, este contiene el estado guardado.
     * @throws RuntimeException Se lanza intencionalmente para probar Firebase Crashlytics.
     * @see [Documentación oficial de Android](https://developer.android.com/guide/components/activities/activity-lifecycle)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        // ...
    }
}
```

### 3.3. Generar Documentación

```bash
./gradlew dokkaHtml
```

La documentación se genera en `docs/`.

---

## 4. Configuración de GitHub Actions

### 4.1. Workflow: Despliegue a GitHub Pages

**Archivo:** `.github/workflows/deploy-docs.yml`

```yaml
name: Deploy Documentation to GitHub Pages

on:
  push:
    branches: ["main"]
  workflow_dispatch:

permissions:
  contents: read
  pages: write
  id-token: write

concurrency:
  group: "pages"
  cancel-in-progress: false

jobs:
  deploy:
    environment:
      name: github-pages
      url: ${{ steps.deployment.outputs.page_url }}
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4
      
      - name: Setup Pages
        uses: actions/configure-pages@v5
      
      - name: Upload artifact
        uses: actions/upload-pages-artifact@v3
        with:
          path: './docs'
      
      - name: Deploy to GitHub Pages
        id: deployment
        uses: actions/deploy-pages@v4
```

### 4.2. Workflow: Sincronización con Wiki

**Archivo:** `.github/workflows/sync-wiki.yml`

```yaml
name: Sync README to Wiki

on:
  push:
    branches: ["main"]
    paths:
      - 'README.md'
  workflow_dispatch:

permissions:
  contents: read

jobs:
  update-wiki:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Generate wiki pages
        run: |
          mkdir -p wiki-content
          
          # Copy README as Home.md
          cp README.md wiki-content/Home.md
          
          # Generate _Sidebar.md
          cat > wiki-content/_Sidebar.md << 'EOF'
          ## Navigation
          
          * [Home](Home)
          * [Instalación](Instalación)
          * [Ejecución](Ejecución)
          * [Estructura](Estructura)
          * [Documentación](Documentación)
          * [API Reference](api-reference)
          
          ---
          
          **Project Info**
          * Version: 1.0
          * Author: Lourdes Rodríguez Morón
          * License: MIT
          EOF
          
          # Create additional pages
          cat > wiki-content/Instalación.md << 'EOF'
          # Instalación y configuración
          
          Para ejecutar este proyecto, necesitarás:
          
          1. Android Studio.
          2. Clonar este repositorio:
             ```bash
             git clone https://github.com/moronlu18/HelloWorldKotlin.git
             ```
          3. Abrir el proyecto en Android Studio.
          EOF
          
          cat > wiki-content/Ejecución.md << 'EOF'
          # Ejecución
          
          Para ejecutar la aplicación:
          
          1. Abre el proyecto en Android Studio.
          2. Ejecuta **Run > Run 'app'**.
          EOF
          
          cat > wiki-content/Estructura.md << 'EOF'
          # Estructura del proyecto
          
          | Archivo | Descripción |
          |---------|-------------|
          | `MainActivity.kt` | Clase principal |
          | `activity_main.xml` | Layout XML |
          | `colors.xml` | Colores |
          | `strings.xml` | Cadenas |
          | `docs/` | Documentación Dokka |
          EOF
          
          cat > wiki-content/Documentación.md << 'EOF'
          # Documentación
          
          Generar documentación:
          
          ```bash
          ./gradlew dokkaHtml
          ```
          EOF
          
          cat > wiki-content/api-reference.md << 'EOF'
          # API Reference
          
          ## MainActivity
          
          ### Propiedades
          - `TAG`: Etiqueta para logs
          - `message`: Mensaje de la interfaz
          - `binding`: Referencia al binding
          
          ### Métodos
          - `onCreate()`: Inicializa la actividad
          - `onStart()`: Se llama cuando es visible
          EOF

      - name: Push to Wiki
        env:
          WIKI_TOKEN: ${{ secrets.WIKI_TOKEN }}
        run: |
          if [ -z "$WIKI_TOKEN" ]; then
            echo "Error: WIKI_TOKEN secret not configured"
            exit 1
          fi
          
          WIKI_URL="https://x-access-token:${WIKI_TOKEN}@github.com/${{ github.repository }}.wiki.git"
          
          git clone "$WIKI_URL" wiki-repo
          cp wiki-content/* wiki-repo/
          cd wiki-repo
          git config user.name "github-actions[bot]"
          git config user.email "github-actions[bot]@users.noreply.github.com"
          git add .
          git diff --staged --quiet || git commit -m "docs: update wiki from README"
          git push
```

### 4.3. Workflow Eliminado (Jekyll)

Se eliminó `.github/workflows/jekyll-gh-pages.yml` porque causaba conflictos.

---

## 5. Configuración de GitHub Pages

### 5.1. Habilitar GitHub Pages

1. Ve a tu repositorio en GitHub
2. **Settings** → **Pages**
3. En **Source**, selecciona: **"Deploy from a branch"**
4. Selecciona rama: **`main`**
5. Selecciona carpeta: **`/docs`**
6. Haz clic en **Save**

### 5.2. Verificar Configuración

La URL de tu documentación será:
```
https://moronlu18.github.io/HelloWorldKotlin/
```

### 5.3. Archivo `.nojekyll`

Creado para deshabilitar el procesamiento de Jekyll:

```
# (archivo vacío)
```

---

## 6. Configuración de la Wiki

### 6.1. Habilitar la Wiki

1. Ve a tu repositorio en GitHub
2. **Settings** → **General**
3. En **Features**, marca **Wikis**
4. Guarda los cambios

### 6.2. Crear Personal Access Token (PAT)

1. Ve a https://github.com/settings/tokens
2. Haz clic en **"Generate new token (classic)"**
3. Nombre: `Wiki Sync Token`
4. Expiración: 90 días (o la que prefieras)
5. Permisos: marca **`repo`** (incluye acceso a wiki)
6. Haz clic en **"Generate token"**
7. **Copia el token** (solo se muestra una vez)

### 6.3. Crear Secreto en GitHub

1. Ve a tu repositorio → **Settings** → **Secrets and variables** → **Actions**
2. Haz clic en **"New repository secret"**
3. Nombre: `WIKI_TOKEN`
4. Valor: pega el token que copiaste
5. Haz clic en **"Add secret"**

### 6.4. Estructura de la Wiki

El workflow crea estas páginas:

| Archivo | Contenido |
|---------|-----------|
| `Home.md` | README.md completo |
| `_Sidebar.md` | Navegación lateral |
| `Instalación.md` | Guía de instalación |
| `Ejecución.md` | Cómo ejecutar la app |
| `Estructura.md` | Estructura del proyecto |
| `Documentación.md` | Cómo generar docs |
| `api-reference.md` | Referencia API |

---

## 7. Estructura de Archivos

### 7.1. Estructura del Repositorio

```
HelloWorldKotlin/
├── .github/
│   └── workflows/
│       ├── deploy-docs.yml        # Despliegue a GitHub Pages
│       └── sync-wiki.yml          # Sincronización con Wiki
├── app/
│   ├── build.gradle.kts           # Configuración Dokka
│   └── src/
│       └── main/
│           └── java/
│               └── com/moronlu18/helloworldkotlin/
│                   └── MainActivity.kt  # Documentado con KDoc
├── docs/                          # Documentación generada por Dokka
│   ├── index.html
│   ├── app/
│   ├── images/
│   ├── scripts/
│   └── styles/
├── .gitignore
├── .nojekyll                      # Deshabilita Jekyll
├── README.md                      # Fuente para la Wiki
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
```

### 7.2. Archivos Importantes

| Archivo | Propósito |
|---------|-----------|
| `app/build.gradle.kts` | Configuración de Dokka |
| `.github/workflows/deploy-docs.yml` | Despliegue a GitHub Pages |
| `.github/workflows/sync-wiki.yml` | Sincronización con Wiki |
| `README.md` | Fuente para Home.md de la Wiki |
| `docs/` | Documentación HTML generada |

---

## 8. Solución de Problemas

### 8.1. Error: "Failed to deploy (completed) - Jekyll"

**Causa:** GitHub Pages está intentando usar Jekyll.

**Solución:**
1. Asegúrate de tener el archivo `.nojekyll`
2. Verifica que Source esté en "Deploy from a branch" → `main` → `/docs`

### 8.2. Error: "Validation Failed (422)"

**Causa:** GitHub Pages no está habilitado.

**Solución:**
1. Ve a Settings → Pages
2. Habilita GitHub Pages
3. Configura Source correctamente

### 8.3. Error: "WIKI_TOKEN secret not configured"

**Causa:** Falta el token de acceso.

**Solución:**
1. Crea un PAT con permiso `repo`
2. Agrégalo como secreto `WIKI_TOKEN`

### 8.4. Error: "could not read Username"

**Causa:** Git no tiene credenciales.

**Solución:**
```bash
# Opción SSH
git remote set-url origin git@github.com:moronlu18/HelloWorldKotlin.git

# Opción HTTPS con token
git remote set-url origin https://<USUARIO>:<TOKEN>@github.com/moronlu18/HelloWorldKotlin.git
```

### 8.5. Regenerar Documentación

Si modificas el código y quieres actualizar la documentación:

```bash
# Regenerar documentación Dokka
./gradlew dokkaHtml

# Los cambios se desplegarán automáticamente al hacer push
git add .
git commit -m "docs: update documentation"
git push
```

---

## Comandos Útiles

```bash
# Generar documentación
./gradlew dokkaHtml

# Ver estado de git
git status

# Hacer push
git push origin main

# Ver workflows
ls -la .github/workflows/

# Verificar documentación generada
ls -la docs/
```

---

## Referencias

- [Dokka Documentation](https://kotlinlang.org/docs/dokka-get-started.html)
- [GitHub Actions](https://docs.github.com/en/actions)
- [GitHub Pages](https://docs.github.com/en/pages)
- [GitHub Wiki](https://docs.github.com/en/commits/managing-a-repository/managing-files-in-a-repository/working-with-files)
- [KDoc Syntax](https://kotlinlang.org/docs/kdoc.html)

---

**Autor:** Lourdes Rodríguez Morón  
**Fecha:** Junio 2026  
**Versión:** 1.0

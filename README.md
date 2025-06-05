# GGManager - Android App

![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![MVVM](https://img.shields.io/badge/Architecture-MVVM-blueviolet?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-In_Development-yellow?style=for-the-badge)

## 📱 Descripción

**GGManager** es una aplicación Android desarrollada en Java para la gestión de grupos y tareas, especialmente pensada para su uso en entornos de *esports*. Actualmente permite:

- Crear y gestionar grupos.
- Asignar roles (admin / miembro).
- Crear, asignar y seguir tareas.
- Editar el perfil de usuario.
- Consultar partidas mediante WebView a través de [op.gg](https://op.gg).

---

## 🚀 Características principales

- 🔐 Autenticación de usuarios (Firebase)
- 👥 Gestión de equipos (crear, editar, invitar)
- 📝 Gestión de tareas por grupo
- 🌐 Visualización de estadísticas de jugadores vía WebView (`op.gg`)
- 🧩 Arquitectura Clean Architecture + MVVM
- 📦 Uso de ViewModel, ViewState, LiveData y ViewBinding

---

## 📂 Estructura del proyecto

```plaintext
ggmanager.frontend.android/
├── core/                  # Utilidades, constantes, helpers
├── data/                  # Repositorios, fuentes de datos (Firebase)
├── di/                    # Módulos de inyección de dependencias (Hilt)
├── domain/                # Capa de dominio
│   ├── model/             # Modelo de datos
│   ├── repository/        # Interfaces de repositorio
│   └── usecase/           # Casos de uso
├── ui/                    # Fragments, Activities y ViewModels organizados por feature
│   ├── auth/              # Login, registro, recuperación
│   ├── home/              # Features principales
│   │    ├── achievement/  # Logros del usuario
│   │    ├── soloq/        # WebView
│   │    ├── team/         # Funciones para los grupos
│   │    └── user/         # Vista y edición de perfil
│   └── login/             # Gestión de grupos
└── GGManagerApp.java      # Punto de entrada principal
````

---

## 🛠️ Tecnologías usadas

* **Lenguaje:** Java
* **Arquitectura:** Clean Architecture + MVVM
* **UI:** ViewBinding + Material Design
* **Autenticación:** Firebase Auth
* **Persistencia:** Firebase Firestore

---

## 🌐 WebView (op.gg)

La app incluye una sección donde los usuarios pueden consultar su historial de partidas y estadísticas directamente desde [op.gg](https://op.gg), mediante un WebView integrado que carga la página del summoner automáticamente.

---

## ⚙️ Instalación y ejecución

1. Clona el repositorio:

   ```bash
   git clone https://github.com/isaurus/ggmanager.frontend.android.git
   ```
2. Abre el proyecto en Android Studio.
3. Conecta un dispositivo o usa un emulador.
4. Ejecuta la app con el botón ▶️.

> Asegúrate de tener configurado Firebase en tu entorno local y de incluir tu archivo `google-services.json` en `app/`.

---

## 📌 Estado del proyecto

🔧 **En desarrollo activo.**
Se irán añadiendo nuevas funcionalidades como notificaciones push, calendario de eventos y mejoras en el flujo de tareas.

---

## 📃 Licencia

Este proyecto aún **no tiene licencia explícita**.
Todos los derechos reservados por el autor.

---

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Si quieres colaborar, abre un issue o haz un pull request con tu propuesta.

---

## 📫 Contacto

Desarrollado por **Isaac [@isaurus](https://github.com/isaurus)**
Si tienes preguntas o sugerencias, ¡no dudes en abrir un issue en el repositorio!

---

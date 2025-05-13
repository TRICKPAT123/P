# FitMatchApp

**FitMatchApp** is a Java Swing-based fitness tracking application designed to help users monitor their fitness progress, track nutrition, log exercises, and assess their body type using a somatotype calculator. The app features an intuitive, modern interface with custom-styled UI elements to enhance the user experience.

## Features

- **User Profile Panel**: Stores user data such as personal details and fitness goals.
- **Nutrition Panel**: Allows users to track their daily food intake and nutritional goals.
- **Exercise Panel**: Provides a space for users to log and track their exercise routines.
- **Progress Panel**: Displays charts and graphs for tracking progress over time.
- **Somatotype Calculator**: Calculates and displays the user's body type based on their height, weight, and waist measurements.
- **Custom UI**: Includes animated tabs, gradient backgrounds, rounded borders, and floating action buttons for a seamless user experience.

## Project Structure

The project is organized as follows:

PROJECT_NAME/
├── .vscode/ # VSCode configurations (if necessary)
├── fonts/ # Font files (Cascadia_Mono, Montserrat)
├── images/ # Image resources (icons, backgrounds, etc.)
├── src/ # Source code
│ ├── logic/ # Logic layer (e.g., calculations, back-end code)
│ ├── models/ # Data models (e.g., fitness models, user data)
│ ├── panels/ # UI panels (e.g., ExercisePanel, NutritionPanel)
│ │ ├── Style/ # Styles and UI-related components (e.g., Rounded borders, Gradient panels)
│ │ │ └── UIUtils.java # Utility classes for styling
│ │ ├── ExercisePanel.java
│ │ ├── NutritionPanel.java
│ │ ├── ProgressPanel.java
│ │ └── UserPanel.java
│ ├── FitMatchApp.java # Main entry point for your app
│ └── CustomButtonExample.java # Example or reusable UI component (if relevant)
└── README.md # Optional documentation for the project


## Setup and Installation

1. **Clone the repository:**


2. **Import the project into your IDE:**
- Open the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).
- If you're using VSCode, open the project folder directly.

3. **Install Dependencies (if any):**
This project is built using Java, so make sure you have JDK installed on your system. The app should be compatible with JDK 8 and above.

4. **Run the application:**
- Navigate to the `src/` directory.
- Compile and run `FitMatchApp.java` to launch the application.

Alternatively, if you're using an IDE, you can directly run the `FitMatchApp` class as the main class.

## Technologies Used

- **Java Swing**: For building the graphical user interface.
- **Java 8+**: The app is compatible with Java 8 and newer versions.
- **Custom UI Components**: Custom buttons, panels, and animations to enhance the user experience.
- **Fonts**: Cascadia Mono and Montserrat for a clean, modern design.

## Screenshots

(Include some screenshots of the app's UI here to give users a preview of the app in action.)

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-name`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add new feature'`).
5. Push to the branch (`git push origin feature-name`).
6. Create a new Pull Request.

## License

This project is open source and available under the [MIT License](LICENSE).

## Acknowledgements

- Special thanks to the contributors and resources used in creating the app.
- Icons and images used in the project are from [icon source].


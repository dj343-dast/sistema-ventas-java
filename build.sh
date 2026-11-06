#!/bin/bash
set -e

TOMCAT_HOME="${TOMCAT_HOME:-C:/tomcat10}"
NOMBRE_APP="sistema-ventas"
CARPETA_APP="build/$NOMBRE_APP"
if ! command -v javac > /dev/null; then
  echo "No se encontro javac. Instale un JDK y abra una terminal nueva."
  exit 1
fi
if [ ! -f "$TOMCAT_HOME/lib/servlet-api.jar" ]; then
  echo "No se encontro $TOMCAT_HOME/lib/servlet-api.jar. Revise TOMCAT_HOME en build.sh."
  exit 1
fi
if [ ! -f config.properties ]; then
  echo "Falta config.properties. Copie config.properties.example con ese nombre."
  exit 1
fi
if ! ls lib/*.jar > /dev/null 2>&1; then
  echo "Falta el .jar del driver de MySQL en la carpeta lib."
  exit 1
fi
case "$(uname -s)" in
  MINGW*|MSYS*|CYGWIN*) SEPARADOR=";" ;;
  *) SEPARADOR=":" ;;
esac
rm -rf build
mkdir -p "$CARPETA_APP/WEB-INF/classes" "$CARPETA_APP/WEB-INF/lib"
cp -r webapp/. "$CARPETA_APP/"
cp lib/*.jar "$CARPETA_APP/WEB-INF/lib/"
cp config.properties "$CARPETA_APP/WEB-INF/classes/"
javac -encoding UTF-8 -cp "$TOMCAT_HOME/lib/servlet-api.jar${SEPARADOR}lib/*" \
  -d "$CARPETA_APP/WEB-INF/classes" $(find src -name "*.java")
  rm -rf "$TOMCAT_HOME/webapps/$NOMBRE_APP"
mkdir -p "$TOMCAT_HOME/webapps"
cp -r "$CARPETA_APP" "$TOMCAT_HOME/webapps/$NOMBRE_APP"

echo "Listo. Aplicacion copiada en $TOMCAT_HOME/webapps/$NOMBRE_APP"
echo "Con Tomcat iniciado, abra http://localhost:8080/$NOMBRE_APP/"

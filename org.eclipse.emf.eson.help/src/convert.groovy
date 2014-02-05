def processFileAndSave(sourceFile, targetFile, Closure processText) {
    def text = sourceFile
    targetFile.write(processText(text))
}

sourceFile = new File('../help/concepts.mediawiki') 
targetFile = new File('../help/concepts_google.mediawiki') 

processFileInplace(sourceFile, targetFile) { text ->
    text.replaceAll("{{{", "")
    text.replaceAll("}}}", "")
}
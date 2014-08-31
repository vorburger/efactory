/*
 * #%L
 * org.eclipse.emf.eson.help
 * %%
 * Copyright (C) 2009 - 2014 Sebastian Benz
 * %%
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * #L%
 */
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
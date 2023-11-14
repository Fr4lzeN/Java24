package com.example.idocument;

import android.net.Uri;

public abstract class IDocumentAbstract implements IDocument{
    public IDocumentAbstract(String fileName, Uri path) {
        this.fileName = fileName;
        this.path = path;
    }

    public IDocumentAbstract() {
    }

    public IDocumentAbstract(String fileName) {
        this.fileName = fileName;
    }

    String fileName;
    Uri path;

}

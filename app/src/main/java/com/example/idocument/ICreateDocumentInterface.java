package com.example.idocument;

import android.net.Uri;

public interface ICreateDocumentInterface {

    IDocument createNew(Uri path);
    IDocument createOpen(Uri path);

}

"use client"

import { useState, useRef } from "react"
import { Upload, X } from "lucide-react"
import { Button } from "@/components/ui/button"

interface UploadedFile {
  id: string
  name: string
  size: string
  date: string
  type: string
}

interface FileUploadProps {
  onFilesChange?: (files: UploadedFile[]) => void
}

export function FileUpload({ onFilesChange }: FileUploadProps) {
  const [uploadedFiles, setUploadedFiles] = useState<UploadedFile[]>([
    { id: "1", name: "File Title.png", size: "313 KB", date: "31 Aug, 2022", type: "PNG" },
    { id: "2", name: "File Title.png", size: "313 KB", date: "31 Aug, 2022", type: "PNG" },
  ])
  const fileInputRef = useRef<HTMLInputElement>(null)

  const handleFileUpload = () => {
    fileInputRef.current?.click()
  }

  const removeFile = (id: string) => {
    const updatedFiles = uploadedFiles.filter((file) => file.id !== id)
    setUploadedFiles(updatedFiles)
    onFilesChange?.(updatedFiles)
  }

  return (
    <div>
      <div className="border-2 border-dashed border-gray-300 rounded-lg p-12 text-center bg-gray-50">
        <div className="flex flex-col items-center">
          <div className="w-16 h-16 bg-purple-100 rounded-full flex items-center justify-center mb-4">
            <Upload className="w-8 h-8 text-purple-600" />
          </div>
          <p className="text-gray-600 mb-2">
            Drag & drop files or{" "}
            <button onClick={handleFileUpload} className="text-purple-600 hover:underline">
              Browse
            </button>
          </p>
          <p className="text-xs text-gray-500">Supported formats: JPEG, PNG, GIF, MP4, PDF, PSD, AI, Word, PPT</p>
        </div>
      </div>

      <input ref={fileInputRef} type="file" multiple className="hidden" accept="image/*,.pdf,.doc,.docx,.ppt,.pptx" />

      <Button onClick={handleFileUpload} variant="outline" className="mt-4 bg-gray-100 hover:bg-gray-200">
        Upload file
      </Button>

      {uploadedFiles.length > 0 && (
        <div className="mt-6">
          <p className="text-sm font-medium text-gray-700 mb-3">Uploaded:</p>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
            {uploadedFiles.map((file) => (
              <div key={file.id} className="flex items-center justify-between bg-gray-100 rounded-lg p-3">
                <div className="flex items-center space-x-3">
                  <div className="w-8 h-8 bg-red-500 rounded flex items-center justify-center">
                    <span className="text-white text-xs font-bold">{file.type}</span>
                  </div>
                  <div>
                    <p className="text-sm font-medium text-gray-900">{file.name}</p>
                    <p className="text-xs text-gray-500">
                      {file.size} · {file.date}
                    </p>
                  </div>
                </div>
                <button onClick={() => removeFile(file.id)} className="text-gray-400 hover:text-gray-600">
                  <X className="w-4 h-4" />
                </button>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  )
}

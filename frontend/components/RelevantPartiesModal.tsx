"use client";
import { toast } from "sonner";
import { useRef, useState, useEffect } from "react";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Card, CardContent } from "@/components/ui/card";
import { useRouter, useSearchParams } from "next/navigation";

export default function RelevantPartiesModal({
  onClose,
  onSubmitted,
}: {
  onClose?: () => void;
  onSubmitted?: () => void;
}) {

  const [files, setFiles] = useState<File[]>([]);
  const [formData, setFormData] = useState({
    fullname: "",
    relation: "",
    gender: "",
    nationality: "",
    description: "",
  });
  const fileInputRef = useRef<HTMLInputElement | null>(null);
  const router = useRouter();
  const searchParams = useSearchParams();
  const id = searchParams.get("id");
  const [form, setForm] = useState<any>({});

  useEffect(() => {
    if (id && typeof window !== "undefined") {
      const data = JSON.parse(
        sessionStorage.getItem("relevantParties") || "[]"
      );
      const found = data.find((item: any) => String(item.id) === String(id));
      if (found) setForm(found);
    }
  }, [id]);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      setFiles([...files, ...Array.from(e.target.files)]);
    }
  };

  const removeFile = (index: number) => {
    setFiles(files.filter((_, i) => i !== index));
  };

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSelectChange = (name: string, value: string) => {
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Lưu dữ liệu vào sessionStorage (tạm thời)
    const errors: string[] = [];

    // Kiểm tra các trường rỗng
    if (!formData.relation) errors.push('Relationship to the incident is required.')
    if (!formData.gender) errors.push('Gender is required.')


    if (errors.length > 0) {
      toast.error("Please fill all required fields:", {
        description: errors.join("\n"),
      });
      return;
    }
    const relevantParties = JSON.parse(
      sessionStorage.getItem("relevantParties") || "[]"
    );
    const newParty = {
      id: relevantParties.length + 1,
      ...formData,
      attachments: files.length > 0 ? `${files.length} files` : 'No attachments'
    }
    sessionStorage.setItem('relevantParties', JSON.stringify([...relevantParties, newParty]))
    // router.push('/reporter') // Quay lại trang chính reporter
    // sessionStorage.setItem('resumeStep', '2') //  Ghi nhớ cần quay lại Step 2
    // router.push('/reporter')                  //  Quay lại MultiStepForm
    if (onSubmitted) onSubmitted();
    if (onClose) onClose();
  }

  const handleCancel = () => {
    // router.push('/reporter') // Điều hướng trở lại reporter
    // sessionStorage.setItem('resumeStep', '2') //  Ghi nhớ cần quay lại step 2
    // router.push('/reporter')
    if (onClose) onClose();
  }

  const formatFileSize = (size: number) => `${(size / 1024).toFixed(0)} KB`;
  const formatDate = () =>
    new Date().toLocaleDateString("en-GB", {
      day: "2-digit",
      month: "short",
      year: "numeric",
    });

  return (
    <Card className="max-w-4xl mx-auto mt-10 bg-white p-8 rounded-xl ">
      <CardContent>
        <h2 className="text-2xl font-bold text-center mb-2">
          Relevant Parties
        </h2>
        <p className="text-sm text-muted-foreground text-center mb-6">
          This form is used to document the roles and identities of all parties
          connected to the incident.
        </p>

        <form className="space-y-6" onSubmit={handleSubmit}>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <Label htmlFor="fullname" className="mb-2 block">
                Full name
              </Label>
              <Input
                id="fullname"
                name="fullname"
                placeholder="E.g., John Michael Doe"
                className="w-full"
                value={formData.fullname}
                onChange={handleInputChange}
                required
              />
            </div>

            <div>
              <Label htmlFor="relation" className="mb-2 block">
                Relationship to the incident *
              </Label>
              <Select
                onValueChange={(value) => handleSelectChange("relation", value)}
              >
                <SelectTrigger id="relation" className="w-full">
                  <SelectValue placeholder="Select an option" />
                </SelectTrigger>
                <SelectContent className="bg-white border border-gray-200 rounded-md shadow-md">
                  <SelectItem value="witness">Witness</SelectItem>
                  <SelectItem value="victim">Victim</SelectItem>
                  <SelectItem value="suspect">Suspect</SelectItem>
                  <SelectItem value="accomplice">Accomplice</SelectItem>
                </SelectContent>
              </Select>
            </div>

            <div>
              <Label htmlFor="gender" className="mb-2 block">
                Gender
              </Label>
              <Select
                onValueChange={(value) => handleSelectChange("gender", value)}
              >
                <SelectTrigger id="gender" className="w-full">
                  <SelectValue placeholder="Select an option" />
                </SelectTrigger>
                <SelectContent className="bg-white border border-gray-200 rounded-md shadow-md">
                  <SelectItem value="male">Male</SelectItem>
                  <SelectItem value="female">Female</SelectItem>
                  <SelectItem value="unknown">Unknown</SelectItem>
                </SelectContent>
              </Select>
            </div>

            <div>
              <Label htmlFor="nationality" className="mb-2 block">
                Nationality
              </Label>
              <Input
                id="nationality"
                name="nationality"
                placeholder="E.g., American"
                className="w-full"
                value={formData.nationality}
                onChange={handleInputChange}
                required
              />
            </div>
          </div>

          <div>
            <Label htmlFor="description" className="mb-2 block">
              Statement / Description
            </Label>
            <Textarea
              id="description"
              name="description"
              placeholder="Provide a clear and detailed description..."
              className="w-full"
              value={formData.description}
              onChange={handleInputChange}
              required
            />
          </div>

          {/* <div>
            <Label>Attachments</Label>
            <div className="border-2 border-dashed rounded-md p-6 text-center mt-2">
              <p className="text-sm mb-1">
                Drag & drop files or{" "}
                <span
                  className="text-blue-600 underline cursor-pointer"
                  onClick={() => fileInputRef.current?.click()}
                >
                  Browse
                </span>
              </p>
              <p className="text-xs text-muted-foreground mb-4">
                Supported formats: JPEG, PNG, GIF, MP4, PDF, DOCX, PPT
              </p>

              <input
                type="file"
                multiple
                onChange={handleFileChange}
                ref={fileInputRef}
                className="hidden"
              />

              <Button
                type="button"
                variant="secondary"
                className="text-muted-foreground"
                onClick={() => fileInputRef.current?.click()}
              >
                Upload file
              </Button>
            </div>

            {files.length > 0 && (
              <div className="mt-4 grid grid-cols-1 sm:grid-cols-2 gap-2">
                {files.map((file, index) => {
                  const ext =
                    file.name.split(".").pop()?.toUpperCase() || "FILE";
                  return (
                    <div
                      key={index}
                      className="flex items-center justify-between px-4 py-2 border rounded-md bg-gray-50"
                    >
                      <div className="flex items-center space-x-3 overflow-hidden">
                        <div className="bg-red-500 text-white text-xs font-bold px-2 py-1 rounded">
                          {ext}
                        </div>

                        <div className="flex flex-col overflow-hidden">
                          <span className="text-sm font-medium truncate">
                            {file.name}
                          </span>
                          <span className="text-xs text-muted-foreground">
                            {formatFileSize(file.size)} · {formatDate()}
                          </span>
                        </div>
                      </div>

                      <Button
                        type="button"
                        size="sm"
                        variant="ghost"
                        onClick={() => removeFile(index)}
                      >
                        ✕
                      </Button>
                    </div>
                  );
                })}
              </div>
            )}
          </div> */}

          <div className="flex justify-end space-x-4 pt-4">
            <Button type="button" variant="outline" onClick={handleCancel}>
              Cancel
            </Button>
            <Button type="submit">Create</Button>
          </div>
        </form>
      </CardContent>
    </Card>
  );
}
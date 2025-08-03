// Step2.tsx
import InitialEvidenceModal from "@/components/InitialEvidenceModal";
import { useState, useEffect } from "react";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import {
  Table,
  TableHeader,
  TableBody,
  TableHead,
  TableRow,
  TableCell,
} from "@/components/ui/table";
import { Calendar } from "@/components/ui/calendar";
import {
  Popover,
  PopoverTrigger,
  PopoverContent,
} from "@/components/ui/popover";
import { format } from "date-fns";
import { Edit, Trash2 } from "lucide-react";
import { useRouter } from "next/navigation";
import RelevantPartiesModal from "../RelevantPartiesModal";

export default function Step2({ data, onNext, onBack }: any) {
  const [form, setForm] = useState(data);
  const [date, setDate] = useState<Date | undefined>(undefined);
  const [open, setOpen] = useState(false);
  const [showConfirm, setShowConfirm] = useState(false);
  const [showDelete, setShowDelete] = useState(false);
  const [deleteTarget, setDeleteTarget] = useState<{
    id: number;
    type: "relevant" | "initial";
  } | null>(null);
  const [relevantParties, setRelevantParties] = useState<any[]>([]);
  const [initialEvidence, setInitialEvidence] = useState<any[]>([]);
  const router = useRouter();
  const [showInitialModal, setShowInitialModal] = useState(false);
  const [showRelevantModal, setShowRelevantModal] = useState(false);
  const [selectedParty, setSelectedParty] = useState<any>(null);
  const [errors, setErrors] = useState<Record<string, string>>({});

  // Load saved data from sessionStorage on component mount
  useEffect(() => {
    const savedRelevant = sessionStorage.getItem("relevantParties");
    const savedEvidence = sessionStorage.getItem("initialEvidence");

    if (savedRelevant) {
      setRelevantParties(JSON.parse(savedRelevant));
    }
    if (savedEvidence) {
      setInitialEvidence(JSON.parse(savedEvidence));
    }
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });

    // Clear error when field is filled
    if (errors[name]) {
      setErrors(prev => {
        const newErrors = { ...prev };
        delete newErrors[name];
        return newErrors;
      });
    }
  };

  const handleSelectChange = (name: string, value: string) => {
    setForm({ ...form, [name]: value });

    // Clear error when field is selected
    if (errors[name]) {
      setErrors(prev => {
        const newErrors = { ...prev };
        delete newErrors[name];
        return newErrors;
      });
    }
  };

  const handleDateChange = (selectedDate: Date | undefined) => {
    setDate(selectedDate);

    // Clear error when date is selected
    if (errors.datetime) {
      setErrors(prev => {
        const newErrors = { ...prev };
        delete newErrors.datetime;
        return newErrors;
      });
    }
  };

  const validateForm = () => {
    const newErrors: Record<string, string> = {};

    if (!form.typeOfCrime) {
      newErrors.typeOfCrime = "Type of crime is required";
    }

    if (!form.severity) {
      newErrors.severity = "Severity is required";
    }

    if (!date) {
      newErrors.datetime = "Date of occurrence is required";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = () => {
    if (!validateForm()) return;
    setShowConfirm(true);
  };

  const handleConfirmYes = () => {
    setShowConfirm(false);
    // Include the date in the form data before submitting
    const formData = {
      ...form,
      datetime: date ? format(date, "yyyy-MM-dd") : null
    };
    onNext(formData);
  };

  const handleDeleteRelevant = (id: number) => {
    setDeleteTarget({ id, type: "relevant" });
    setShowDelete(true);
  };

  const handleDeleteEvidence = (id: number) => {
    setDeleteTarget({ id, type: "initial" });
    setShowDelete(true);
  };

  const handleDeleteYes = () => {
    if (!deleteTarget) return;

    if (deleteTarget.type === "relevant") {
      const updated = relevantParties.filter(
        (item: any) => item.id !== deleteTarget.id
      );
      setRelevantParties(updated);
      sessionStorage.setItem("relevantParties", JSON.stringify(updated));
    } else {
      const updated = initialEvidence.filter(
        (item: any) => item.id !== deleteTarget.id
      );
      setInitialEvidence(updated);
      sessionStorage.setItem("initialEvidence", JSON.stringify(updated));
    }

    setShowDelete(false);
    setDeleteTarget(null);
  };

  return (
    <div className="w-full max-w-screen-md mx-auto py-8">
      {/* Incident Information */}
      <div className="my-8">
        <div className="flex items-center mb-8">
          <div className="flex-1 border-t border-gray-300" />
          <h2 className="mx-4 font-semibold text-lg sm:text-2xl">
            Incident Information
          </h2>
          <div className="flex-1 border-t border-gray-300" />
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-x-10 gap-y-6">
          {/* Type of Crime */}
          <div className="space-y-2">
            <Label htmlFor="typeOfCrime" className="text-base font-semibold">
              Type of crime <span className="text-red-500">*</span>
            </Label>
            <Select
              onValueChange={(value) => handleSelectChange("typeOfCrime", value)}
              value={form.typeOfCrime}
            >
              <SelectTrigger className="w-full">
                <SelectValue placeholder="Select an option" />
              </SelectTrigger>
              <SelectContent className="bg-white border border-gray-200 rounded-md shadow-md">
                <SelectItem value="crimes-against-persons">
                  Crimes Against Persons
                </SelectItem>
                <SelectItem value="crimes-against-property">
                  Crimes Against Property
                </SelectItem>
                <SelectItem value="white-collar-crimes">
                  White-Collar Crimes
                </SelectItem>
                <SelectItem value="cyber-crimes">Cyber Crimes</SelectItem>
                <SelectItem value="drug-related-crimes">
                  Drug-related Crimes
                </SelectItem>
                <SelectItem value="public-order-crimes">
                  Public Order Crimes
                </SelectItem>
              </SelectContent>
            </Select>
            {errors.typeOfCrime && (
              <p className="text-red-500 text-sm mt-1">{errors.typeOfCrime}</p>
            )}
          </div>

          {/* Severity */}
          <div className="space-y-2">
            <Label htmlFor="severity" className="text-base font-semibold">
              Severity <span className="text-red-500">*</span>
            </Label>
            <Select
              onValueChange={(value) => handleSelectChange("severity", value)}
              value={form.severity}
            >
              <SelectTrigger className="w-full">
                <SelectValue placeholder="Select an option" />
              </SelectTrigger>
              <SelectContent className="bg-white border border-gray-200 rounded-md shadow-md">
                <SelectItem value="minor">Minor</SelectItem>
                <SelectItem value="moderate">Moderate</SelectItem>
                <SelectItem value="serious">Serious</SelectItem>
                <SelectItem value="critical">Critical</SelectItem>
              </SelectContent>
            </Select>
            {errors.severity && (
              <p className="text-red-500 text-sm mt-1">{errors.severity}</p>
            )}
          </div>
        </div>

        {/* Date of Occurrence */}
        <div className="mt-6 space-y-2">
          <Label htmlFor="datetime" className="text-base font-semibold">
            Datetime of occurrence <span className="text-red-500">*</span>
          </Label>
          <Popover open={open} onOpenChange={setOpen}>
            <PopoverTrigger asChild>
              <Button
                variant="outline"
                className="w-full justify-start text-left font-normal"
              >
                {date ? (
                  format(date, "dd/MM/yyyy")
                ) : (
                  <span className="text-muted-foreground">Choose</span>
                )}
              </Button>
            </PopoverTrigger>
            <PopoverContent className="w-auto p-0" align="start">
              <Calendar
                mode="single"
                selected={date}
                onSelect={handleDateChange}
                initialFocus
              />
            </PopoverContent>
          </Popover>
          {errors.datetime && (
            <p className="text-red-500 text-sm mt-1">{errors.datetime}</p>
          )}
        </div>

        {/* Detailed Address */}
        <div className="mt-6 space-y-2">
          <Label htmlFor="address" className="text-base font-semibold">
            Detailed address
          </Label>
          <Input
            type="text"
            name="address"
            className="w-full"
            placeholder="Enter detailed address"
            value={form.address || ""}
            onChange={handleChange}
          />
        </div>

        {/* Description */}
        <div className="mt-6 space-y-2">
          <Label htmlFor="description" className="text-base font-semibold">
            Description of the incident
          </Label>
          <Textarea
            name="description"
            placeholder="Briefly describe what happened, including key facts such as time, location, and main events."
            className="w-full"
            value={form.description || ""}
            onChange={handleChange}
          />
        </div>
      </div>

      {/* Relevant Parties */}
      <div className="my-8">
        <div className="flex items-center mb-4">
          <div className="flex-1 border-t border-gray-300" />
          <h2 className="mx-4 font-semibold text-lg sm:text-2xl">
            Relevant Parties
          </h2>
          <div className="flex-1 border-t border-gray-300" />
        </div>
        <div className="overflow-x-auto rounded-lg border border-gray-200 bg-white">
          <Table>
            <TableHeader>
              <TableRow className="bg-[#F8F8F8]">
                <TableHead className="text-center font-semibold">ID</TableHead>
                <TableHead className="text-center font-semibold">Relevant Role</TableHead>
                <TableHead className="text-center font-semibold">Name</TableHead>
                <TableHead className="text-center font-semibold">Statement</TableHead>
                <TableHead className="text-center font-semibold">Attachments</TableHead>
                <TableHead className="text-center font-semibold">Action</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {relevantParties.length === 0 ? (
                <TableRow>
                  <TableCell colSpan={6} className="text-center text-gray-400">
                    No data
                  </TableCell>
                </TableRow>
              ) : (
                relevantParties.map((party: any) => (
                  <TableRow key={party.id} className="border-t border-gray-200">
                    <TableCell className="text-center font-medium">
                      #{party.id}
                    </TableCell>
                    <TableCell className="text-center">
                      {party.relation}
                    </TableCell>
                    <TableCell className="text-center">
                      {party.fullname || "—"}
                    </TableCell>
                    <TableCell className="text-center max-w-xs truncate">
                      {party.description || "—"}
                    </TableCell>
                    <TableCell className="text-center">
                      <span className="text-[#3B82F6]">
                        {party.attachments}
                      </span>
                    </TableCell>
                    <TableCell className="text-center">
                      <button
                        className="inline-flex items-center mr-2 text-[#6C63FF] hover:text-blue-700"
                        onClick={() => {
                          setSelectedParty(party);
                          setShowRelevantModal(true);
                        }}
                      >
                        <Edit size={18} />
                      </button>
                      <button
                        className="inline-flex items-center text-[#F44336] hover:text-red-700"
                        onClick={() => handleDeleteRelevant(party.id)}
                      >
                        <Trash2 size={18} />
                      </button>
                    </TableCell>
                  </TableRow>
                ))
              )}
            </TableBody>
          </Table>
        </div>
        <div className="flex justify-end mt-2">
          <Button
            variant="outline"
            className="bg-[#F3F6F9] text-[#434343] font-semibold rounded-md px-8"
            onClick={() => setShowRelevantModal(true)}
          >
            ADD
          </Button>
        </div>
      </div>

      {/* Initial Evidence */}
      <div className="my-8">
        <div className="flex items-center mb-4">
          <div className="flex-1 border-t border-gray-300" />
          <h2 className="mx-4 font-semibold text-lg sm:text-2xl">
            Initial Evidence
          </h2>
          <div className="flex-1 border-t border-gray-300" />
        </div>
        <div className="overflow-x-auto rounded-lg border border-gray-200 bg-white">
          <Table>
            <TableHeader>
              <TableRow className="bg-[#F8F8F8]">
                <TableHead className="text-center font-semibold">ID</TableHead>
                <TableHead className="text-center font-semibold">Types of Evidence</TableHead>
                <TableHead className="text-center font-semibold">Location</TableHead>
                <TableHead className="text-center font-semibold">Description</TableHead>
                <TableHead className="text-center font-semibold">Attachments</TableHead>
                <TableHead className="text-center font-semibold">Action</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {initialEvidence.length === 0 ? (
                <TableRow>
                  <TableCell colSpan={6} className="text-center text-gray-400">
                    No data
                  </TableCell>
                </TableRow>
              ) : (
                initialEvidence.map((evidence: any) => (
                  <TableRow key={evidence.id} className="border-t border-gray-200">
                    <TableCell className="text-center font-medium">
                      #{evidence.id}
                    </TableCell>
                    <TableCell className="text-center">
                      {evidence.evidenceType}
                    </TableCell>
                    <TableCell className="text-center">
                      {evidence.location}
                    </TableCell>
                    <TableCell className="text-center max-w-xs truncate">
                      {evidence.description}
                    </TableCell>
                    <TableCell className="text-center">
                      <span className="text-[#3B82F6]">
                        {evidence.attachments}
                      </span>
                    </TableCell>
                    <TableCell className="text-center">
                      <button
                        className="inline-flex items-center mr-2 text-[#6C63FF] hover:text-blue-700"
                        onClick={() => router.push(`/reporter/initial?id=${evidence.id}`)}
                      >
                        <Edit size={18} />
                      </button>
                      <button
                        className="inline-flex items-center text-[#F44336] hover:text-red-700"
                        onClick={() => handleDeleteEvidence(evidence.id)}
                      >
                        <Trash2 size={18} />
                      </button>
                    </TableCell>
                  </TableRow>
                ))
              )}
            </TableBody>
          </Table>
        </div>
        <div className="flex justify-end mt-2">
          <Button
            variant="outline"
            className="bg-[#F3F6F9] text-[#434343] font-semibold rounded-md px-8"
            onClick={() => setShowInitialModal(true)}
          >
            ADD
          </Button>
        </div>
      </div>

      {/* Navigation Buttons */}
      <div className="flex justify-end gap-4 mt-8">
        <Button variant="outline" className="w-32" onClick={onBack}>
          Back
        </Button>
        <Button className="w-32 bg-black text-white" onClick={handleSubmit}>
          Submit
        </Button>
      </div>

      {/* Confirmation Modal */}
      {showConfirm && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/30">
          <div className="bg-white rounded-xl shadow-xl p-8 w-full max-w-md">
            <div className="flex items-start gap-4 mb-4">
              <div className="w-2 h-10 rounded bg-blue-300" />
              <div>
                <div className="text-xl font-bold mb-1">
                  Declaration & Confirmation
                </div>
                <ol className="text-gray-700 text-sm list-decimal pl-5">
                  <li>
                    I hereby declare that all the information provided in this
                    report is true and accurate to the best of my knowledge.
                  </li>
                  <li>
                    I accept full legal responsibility for any false or
                    misleading information submitted.
                  </li>
                </ol>
              </div>
            </div>
            <div className="flex justify-end gap-4 mt-6">
              <Button variant="outline" onClick={() => setShowConfirm(false)}>
                Cancel
              </Button>
              <Button className="bg-black text-white" onClick={handleConfirmYes}>
                Yes
              </Button>
            </div>
          </div>
        </div>
      )}

      {/* Delete Confirmation Modal */}
      {showDelete && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/30">
          <div className="bg-white rounded-xl shadow-xl p-8 w-full max-w-md">
            <div className="flex items-start gap-4 mb-4">
              <div className="w-2 h-10 rounded bg-red-200" />
              <div>
                <div className="text-xl font-bold mb-1 text-red-600">
                  Delete
                </div>
                <div className="text-gray-700 text-sm">
                  Are you sure you want to delete this record?
                </div>
              </div>
            </div>
            <div className="flex justify-end gap-4 mt-6">
              <Button
                variant="outline"
                onClick={() => {
                  setShowDelete(false);
                  setDeleteTarget(null);
                }}
              >
                Cancel
              </Button>
              <Button className="bg-black text-white" onClick={handleDeleteYes}>
                Yes
              </Button>
            </div>
          </div>
        </div>
      )}

      {/* Relevant Parties Modal */}

      {showRelevantModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/30">
          <div className="bg-white p-4 rounded-xl w-[90%] max-w-4xl max-h-[90vh] overflow-auto">
            <RelevantPartiesModal
              onClose={() => setShowRelevantModal(false)}
              onSubmitted={() => {
                setShowRelevantModal(false);
                const data = JSON.parse(sessionStorage.getItem("relevantParties") || "[]");
                setRelevantParties(data);
              }}
            />
          </div>
        </div>
      )}
      {/* Initial Evidence Modal */}
      {showInitialModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/30">
          <div className="bg-white p-4 rounded-xl w-[90%] max-w-4xl max-h-[90vh] overflow-auto">
            <InitialEvidenceModal
              onClose={() => setShowInitialModal(false)}
              onSubmitted={() => {
                setShowInitialModal(false);
                const data = JSON.parse(sessionStorage.getItem("initialEvidence") || "[]");
                setInitialEvidence(data);
              }}
            />
          </div>
        </div>
      )}
    </div>
  );
}
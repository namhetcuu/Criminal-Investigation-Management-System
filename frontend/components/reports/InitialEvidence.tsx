import { FileText } from "lucide-react";

export function InitialEvidence() {
  const evidence = [
    {
      id: "#1",
      type: "Digital Evidence",
      location: "My house (1234 Maplewood Avenue Florida)",
      description: "Image extracted from surveillance camera",
      attachment: "File Title.mp4",
    },
  ];

  const uploadedFiles = [
    {
      name: "File Title.mp4",
      size: "214 KB",
      date: "31 Aug 2022",
    },
  ];

  return (
    <div className="border-b-2 border-gray-200 pb-8">
      <h3 className="text-blue-600 font-semibold mb-6 text-base">
        II. Initial Evidence
      </h3>

      <div className="overflow-x-auto border border-gray-300 rounded-lg mb-6">
        <table className="w-full">
          <thead className="bg-gray-100 border-b-2 border-gray-300">
            <tr>
              <th className="px-4 py-3 text-left text-sm font-semibold text-gray-900 border-r border-gray-300">
                ID
              </th>
              <th className="px-4 py-3 text-left text-sm font-semibold text-gray-900 border-r border-gray-300">
                Type
              </th>
              <th className="px-4 py-3 text-left text-sm font-semibold text-gray-900 border-r border-gray-300">
                Evidence Location
              </th>
              <th className="px-4 py-3 text-left text-sm font-semibold text-gray-900 border-r border-gray-300">
                Description
              </th>
              <th className="px-4 py-3 text-left text-sm font-semibold text-gray-900">
                Attachments
              </th>
            </tr>
          </thead>
          <tbody>
            {evidence.map((item, index) => (
              <tr
                key={item.id}
                className={`${
                  index !== evidence.length - 1
                    ? "border-b border-gray-200"
                    : ""
                }`}
              >
                <td className="px-4 py-3 text-sm text-gray-900 border-r border-gray-200">
                  {item.id}
                </td>
                <td className="px-4 py-3 text-sm text-gray-900 border-r border-gray-200">
                  {item.type}
                </td>
                <td className="px-4 py-3 text-sm text-gray-900 border-r border-gray-200">
                  {item.location}
                </td>
                <td className="px-4 py-3 text-sm text-gray-900 border-r border-gray-200">
                  {item.description}
                </td>
                <td className="px-4 py-3 text-sm text-gray-900">
                  {item.attachment}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {uploadedFiles.length > 0 && (
        <div className="border-t border-gray-200 pt-6">
          <p className="text-sm text-gray-600 mb-4 font-medium">Uploaded:</p>
          <div className="space-y-3">
            {uploadedFiles.map((file, index) => (
              <div
                key={index}
                className="flex items-center gap-3 p-4 bg-blue-50 border border-blue-200 rounded-lg w-fit shadow-sm"
              >
                <div className="w-10 h-10 bg-blue-600 rounded-lg flex items-center justify-center shadow-sm">
                  <FileText className="w-5 h-5 text-white" />
                </div>
                <div>
                  <p className="text-sm font-medium text-gray-900">
                    {file.name}
                  </p>
                  <p className="text-xs text-gray-500">
                    {file.size} - {file.date}
                  </p>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}

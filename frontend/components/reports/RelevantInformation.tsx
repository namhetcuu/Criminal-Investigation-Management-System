import { InitialEvidence } from "./InitialEvidence";
import { RelevantParties } from "./RelavantParties";

export function RelevantInformation() {
  return (
    <div className="mb-8 pb-8 border-b-2 border-gray-200">
      <h2 className="text-red-600 text-lg font-bold mb-8">
        RELEVANT INFORMATION
      </h2>
      <div className="space-y-8">
        <RelevantParties />
        <InitialEvidence />
      </div>
    </div>
  );
}

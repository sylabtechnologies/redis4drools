from sortedcontainers import SortedList

class Solution:
    def countSmaller(self, nums: List[int]) -> List[int]:
        srt = SortedList()
        ret = []

        for x in reversed(nums) :
            cnt = srt.bisect_left(x)    # find where the element would be inserted
            srt.add(x)                  # add the element
            ret.append(cnt)

        return reversed(ret)
